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
                      @click="selectOption('age', option)"
                      :class="['option-button', selectedOptions.age === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

          <!-- 유동인구 -->
          <div>
            <h3 class="text-lg font-semibold mb-2">유동인구</h3>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in footTraffics" :key="option"
                      @click="selectOption('mobility', option)"
                      :class="['option-button', selectedOptions.mobility === option ? 'selected' : '']">
                {{ option }}
              </button>
            </div>
          </div>

          <!-- 물가 -->
          <div>
            <h3 class="text-lg font-semibold mb-2">물가</h3>
            <div class="grid grid-cols-3 gap-2 mb-2">
              <button v-for="option in nearbyPrices" :key="option"
                      @click="selectOption('price', option)"
                      :class="['option-button', selectedOptions.price === option ? 'selected' : '']">
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
import axios from 'axios'

const selectedOptions = ref({
  age: '',
  mobility: '',
  price: '',
  atmosphere: ''
})

const isSubmitted = ref(false)
const evaluationId = ref(null)

const ageGroups = ref([])
const footTraffics = ref([])
const nearbyPrices = ref([])
const atmospheres = ref([])

const fetchTypeList = async () => {
  try {
    console.log('타입 데이터를 불러옵니다...');
    const response = await axios.get('https://j11d108.p.ssafy.io/api/area-evaluation/type');
    const data = response.data;
    console.log('타입 데이터:', data); // API 응답 데이터 출력
    ageGroups.value = data.age_groups;
    footTraffics.value = data.foot_traffics;
    nearbyPrices.value = data.nearby_prices;
    atmospheres.value = data.atmospheres;
  } catch (error) {
    console.error('타입 데이터 불러오기 실패:', error);
    console.error('API 응답:', error.response); // 추가 에러 정보 출력
  }
};


const selectOption = (category, option) => {
  selectedOptions.value[category] = option
}

const createEvaluation = async () => {
  try {
    const response = await axios.post('https://j11d108.p.ssafy.io/api/area-evaluation', selectedOptions.value)
    console.log('평가 제출 성공:', response.data)
    evaluationId.value = response.data.id
    isSubmitted.value = true
  } catch (error) {
    console.error('평가 제출 실패:', error)
  }
}

const updateEvaluation = async () => {
  if (!evaluationId.value) return
  try {
    const response = await axios.put(`https://j11d108.p.ssafy.io/api/area-evaluation/${evaluationId.value}`, selectedOptions.value)
    console.log('평가 수정 성공:', response.data)
  } catch (error) {
    console.error('평가 수정 실패:', error)
  }
}

const deleteEvaluation = async () => {
  if (!evaluationId.value) return
  try {
    await axios.delete(`https://j11d108.p.ssafy.io/api/area-evaluation/${evaluationId.value}`)
    console.log('평가 삭제 성공')
    isSubmitted.value = false
    selectedOptions.value = { age: '', mobility: '', price: '', atmosphere: '' }
  } catch (error) {
    console.error('평가 삭제 실패:', error)
  }
}

onMounted(() => {
  fetchTypeList()
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