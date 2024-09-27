<template>
  <div class="min-h-screen bg-gray-100 flex items-center justify-center p-4">
    <div class="w-full max-w-2xl bg-white rounded-lg shadow-xl overflow-hidden">
      <div class="p-6">
        <h2 class="text-2xl font-bold mb-6">평가하기</h2>

        <div class="space-y-6">
          <!-- 주 연령층 -->
          <div>
            <h3 class="text-lg font-semibold mb-2">주 연령층</h3>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in ageGroups" :key="option"
                      @click="selectOption('ageGroup', option)"
                      :class="['option-button', selectedOptions.ageGroup === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

          <!-- 유동인구 -->
          <div>
            <h3 class="text-lg font-semibold mb-2">유동인구</h3>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in footTraffics" :key="option"
                      @click="selectOption('footTraffic', option)"
                      :class="['option-button', selectedOptions.footTraffic === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

          <!-- 물가 -->
          <div>
            <h3 class="text-lg font-semibold mb-2">물가</h3>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in nearbyPrices" :key="option"
                      @click="selectOption('nearbyPrices', option)"
                      :class="['option-button', selectedOptions.nearbyPrices === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

          <!-- 분위기 -->
          <div>
            <h3 class="text-lg font-semibold mb-2">분위기</h3>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in atmospheres" :key="option"
                      @click="selectOption('atmosphere', option)"
                      :class="['option-button', selectedOptions.atmosphere === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

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
import { ref, onMounted } from 'vue'
import { postAreaEvaluation, putAreaEvaluation, deleteAreaEvaluation, getAllAreaEvaluationType, getMyAreaEvaluation } from '@/api/analytic';
import axios from 'axios'

const selectedOptions = ref({
  ageGroup: '',
  footTraffic: '',
  nearbyPrices: '',
  atmosphere: ''
})

const isSubmitted = ref(false)
const evaluationId = ref(null)
const areaId = ref('3001491'); // 임시로 이태원 관광특구로 설정

const ageGroups = ref([])
const footTraffics = ref([])
const nearbyPrices = ref([])
const atmospheres = ref([])

const fetchTypeList = async () => {
  try {
    getAllAreaEvaluationType(areaId.value, null, (response) => {
      const data = response.data;
      console.log('타입 데이터:', data);
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


const selectOption = (category, option) => {
  selectedOptions.value[category] = option;
}

// 평가 생성 함수
const createEvaluation = async () => {
  try {
    const evaluationData = {
      age_group: selectedOptions.value.ageGroup || '',
      foot_traffic: selectedOptions.value.footTraffic || '',
      atmosphere: selectedOptions.value.atmosphere || '',
      nearby_prices: selectedOptions.value.nearbyPrices || ''
    };

    // 필수 필드가 비어있으면 로그 출력 및 함수 종료
    if (!evaluationData.age_group || !evaluationData.foot_traffic || !evaluationData.atmosphere || !evaluationData.nearby_prices) {
      console.error('모든 필드를 입력해야 합니다.');
      return;
    }

    console.log('전송 데이터', JSON.stringify(evaluationData))

    postAreaEvaluation(areaId.value, evaluationData, 
    (response) => {
      console.log('평가 제출 성공:', response);
      evaluationId.value = response.data.evaluation_Id;
      console.log(response)
      isSubmitted.value = true;
    }, 
    (error) => {
      console.error('평가 제출 실패:', error);
    });
  } catch (error) {
    console.error('평가 제출 실패:', error);
  }
};

// 평가 수정 함수
const updateEvaluation = async () => {
  console.log('evaluationId:', evaluationId.value);
  if (!evaluationId.value) return;
  
  try {
    const evaluationData = {
      age_group: selectedOptions.value.ageGroup || '',
      foot_traffic: selectedOptions.value.footTraffic || '',
      atmosphere: selectedOptions.value.atmosphere || '',
      nearby_prices: selectedOptions.value.nearbyPrices || ''
    };

    // 필수 필드가 비어있으면 로그 출력 및 함수 종료
    if (!evaluationData.age_group || !evaluationData.foot_traffic || !evaluationData.atmosphere || !evaluationData.nearby_prices) {
      console.error('모든 필드를 입력해야 합니다.');
      return;
    }

    // 평가 수정 요청
    putAreaEvaluation(areaId.value, evaluationId.value, evaluationData, (response) => {
      console.log('평가 수정 성공:', response);
    }, (error) => {
      console.error('평가 수정 실패:', error);
    });

  } catch (error) {
    console.error('평가 수정 실패:', error);
  }
};

// 평가 삭제 함수
const deleteEvaluation = async () => {
  if (!evaluationId.value) return;
  try {
    deleteAreaEvaluation(areaId.value, evaluationId.value, (response) => {
      console.log('평가 삭제 성공');
      isSubmitted.value = false;
      selectedOptions.value = { ageGroup: '', footTraffic: '', nearbyPrices: '', atmosphere: '' };
    }, (error) => {
      console.error('평가 삭제 실패:', error);
    });
  } catch (error) {
    console.error('평가 삭제 실패:', error);
  }
};

// 내 평가 불러오기 함수
const fetchMyEvaluation = async () => {
  try {
    getMyAreaEvaluation(areaId.value, evaluationId.value, (response) => {
      const data = response.data;
      console.log('내 평가 데이터:', data);

      evaluationId.value = data.evaluation_id
      console.log('evaluationId:', evaluationId.value);

      // 서버에서 받은 데이터를 selectedOptions에 반영
      selectedOptions.value.ageGroup = data.age_group;
      selectedOptions.value.footTraffic = data.foot_traffic;
      selectedOptions.value.nearbyPrices = data.nearby_prices;
      selectedOptions.value.atmosphere = data.atmosphere;

      // 이미 제출된 평가가 있음을 나타냄
      isSubmitted.value = true;
    }, (error) => {
      console.error('내 평가 데이터 불러오기 실패:', error);
    });
  } catch (error) {
    console.error('내 평가 데이터 불러오기 실패:', error);
  }
};

onMounted(() => {
  fetchTypeList();
  fetchMyEvaluation();
})
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