<template>
  <div id=surveyForm class="min-h-screen bg-gray-100 flex items-center justify-center p-4 card mb-3 shadow-sm" >
    <div class="w-full max-w-2xl bg-white rounded-lg shadow-xl overflow-hidden">
      <div class="p-6">
        <h4>평가하기</h4>
        <hr>
        <div class="space-y-6">
          <!-- 주 연령층 -->
          <div>
            <b>주 연령층</b>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in ageGroups" :key="option" @click="selectOption('ageGroup', option)"
                :class="['option-button', selectedOptions.ageGroup === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

          <!-- 유동인구 -->
          <div>
            <b>유동인구</b>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in footTraffics" :key="option" @click="selectOption('footTraffic', option)"
                :class="['option-button', selectedOptions.footTraffic === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

          <!-- 물가 -->
          <div>
            <b>물가</b>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in nearbyPrices" :key="option" @click="selectOption('nearbyPrices', option)"
                :class="['option-button', selectedOptions.nearbyPrices === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

          <!-- 분위기 -->
          <div>
            <b>분위기</b>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in atmospheres" :key="option" @click="selectOption('atmosphere', option)"
                :class="['option-button', selectedOptions.atmosphere === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>
          <hr>
          <!-- 평가 기능 버튼 -->
          <div class="flex justify-end mt-6 space-x-2">
            <button v-if="!isSubmitted" @click="createEvaluation" class="action-button submit">제출하기</button>
            <button v-if="isSubmitted" @click="updateEvaluation" class="action-button edit">수정하기</button>
            <button v-if="isSubmitted" @click="deleteEvaluation" class="action-button delete">삭제하기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import { postAreaEvaluation, putAreaEvaluation, deleteAreaEvaluation, getAllAreaEvaluationType, getMyAreaEvaluation } from '@/api/analytic';

const props = defineProps({
  place: String // 외부에서 전달되는 place (areaId 역할)
});

const selectedOptions = ref({
  ageGroup: '',
  footTraffic: '',
  nearbyPrices: '',
  atmosphere: ''
});

const isSubmitted = ref(false);
const evaluationId = ref(null);

const ageGroups = ref([]);
const footTraffics = ref([]);
const nearbyPrices = ref([]);
const atmospheres = ref([]);

// 옵션을 선택하는 함수
const selectOption = (category, option) => {
  selectedOptions.value[category] = option;
};

// 평가 데이터를 받아오는 함수
const fetchTypeList = async () => {
  if (!props.place) return; // place가 없으면 종료

  try {
    getAllAreaEvaluationType(props.place, null, (response) => {
      const data = response.data;
      ageGroups.value = data.age_groups;
      footTraffics.value = data.foot_traffics;
      nearbyPrices.value = data.nearby_prices;
      atmospheres.value = data.atmospheres;
    }, (error) => {
      console.error('타입 데이터 불러오기 실패:', error);
    });
  } catch (error) {
    console.error('타입 데이터 불러오기 실패:', error);
  }
};

// 내 평가 데이터를 불러오는 함수
const fetchMyEvaluation = async () => {
  if (!props.place) return; // place가 없으면 종료

  try {
    getMyAreaEvaluation(props.place, evaluationId.value, (response) => {
      const data = response.data;
      evaluationId.value = data.evaluation_id;
      selectedOptions.value.ageGroup = data.age_group;
      selectedOptions.value.footTraffic = data.foot_traffic;
      selectedOptions.value.nearbyPrices = data.nearby_prices;
      selectedOptions.value.atmosphere = data.atmosphere;
      isSubmitted.value = true;
    }, (error) => {
      console.error('내 평가 데이터 불러오기 실패:', error);
    });
  } catch (error) {
    console.error('내 평가 데이터 불러오기 실패:', error);
  }
};

// 평가 생성 함수
const createEvaluation = async () => {
  try {
    const evaluationData = {
      age_group: selectedOptions.value.ageGroup || '',
      foot_traffic: selectedOptions.value.footTraffic || '',
      atmosphere: selectedOptions.value.atmosphere || '',
      nearby_prices: selectedOptions.value.nearbyPrices || ''
    };

    if (!evaluationData.age_group || !evaluationData.foot_traffic || !evaluationData.atmosphere || !evaluationData.nearby_prices) {
      console.error('모든 필드를 입력해야 합니다.');
      return;
    }

    postAreaEvaluation(props.place, evaluationData, (response) => {
      evaluationId.value = response.data.evaluation_Id;
      isSubmitted.value = true;

      // 평가 제출 후 평가 데이터를 다시 불러옴
      fetchMyEvaluation();

      // 성공 알림
      window.alert('평가가 성공적으로 제출되었습니다!');
    }, (error) => {
      console.error('평가 제출 실패:', error);
    });
  } catch (error) {
    console.error('평가 제출 실패:', error);
  }
};

// 평가 수정 함수
const updateEvaluation = async () => {
  if (!evaluationId.value) return;

  try {
    const evaluationData = {
      age_group: selectedOptions.value.ageGroup || '',
      foot_traffic: selectedOptions.value.footTraffic || '',
      atmosphere: selectedOptions.value.atmosphere || '',
      nearby_prices: selectedOptions.value.nearbyPrices || ''
    };

    if (!evaluationData.age_group || !evaluationData.foot_traffic || !evaluationData.atmosphere || !evaluationData.nearby_prices) {
      console.error('모든 필드를 입력해야 합니다.');
      window.alert('모든 필드를 입력해야 합니다.');
      return;
    }

    putAreaEvaluation(props.place, evaluationId.value, evaluationData, (response) => {
      console.log('평가 수정 성공:', response);

      // 성공 알림
      window.alert('평가가 성공적으로 수정되었습니다!');
    }, (error) => {
      console.error('평가 수정 실패:', error);
      window.alert('평가 수정에 실패했습니다.');
    });
  } catch (error) {
    console.error('평가 수정 실패:', error);
    window.alert('평가 수정에 실패했습니다.');
  }
};

// 평가 삭제 함수
const deleteEvaluation = async () => {
  if (!evaluationId.value) return;

  try {
    deleteAreaEvaluation(props.place, evaluationId.value, (response) => {
      isSubmitted.value = false;
      selectedOptions.value = { ageGroup: '', footTraffic: '', nearbyPrices: '', atmosphere: '' };

      // 성공 알림
      window.alert('평가가 성공적으로 삭제되었습니다!');
    }, (error) => {
      console.error('평가 삭제 실패:', error);
      window.alert('평가 삭제에 실패했습니다.');
    });
  } catch (error) {
    console.error('평가 삭제 실패:', error);
  }
};

// place가 변경될 때 데이터를 다시 불러옴
watch(() => props.place, (newPlace) => {
  console.log('새로운 place:', newPlace);
  if (newPlace) {
    fetchTypeList();
    fetchMyEvaluation();
  }
});

onMounted(() => {
  console.log('초기 place:', props.place);
  fetchTypeList();
  fetchMyEvaluation();
});
</script>

<style scoped>
.option-button {
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  border: 1px solid #e2e8f0;
  background-color: white;
  color: #4a5568;
  transition: all 0.2s ease-in-out;
}

.option-button:hover {
  background-color: #f7fafc;
}

.option-button.selected {
  background-color: #48bb78;
  color: white;
  border-color: #48bb78;
}

.action-button {
  padding: 0.5rem 1rem;
  border-radius: 0.375rem;
  font-size: 0.875rem;
  font-weight: 600;
  transition: all 0.2s ease-in-out;
}

.action-button.submit {
  background-color: #48bb78;
  color: white;
}

.action-button.submit:hover {
  background-color: #38a169;
}

.action-button.edit {
  background-color: #48bb78;
  color: white;
}

.action-button.edit:hover {
  background-color: #38a169;
}

.action-button.delete {
  background-color: #e2e8f0;
  color: #4a5568;
}

.action-button.delete:hover {
  background-color: #cbd5e0;
}

@media (max-width: 640px) {
  .grid {
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>
