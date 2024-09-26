<template>
    <div class="min-h-screen bg-gray-100 flex items-center justify-center p-4">
      <div class="w-full max-w-2xl bg-white rounded-lg shadow-xl overflow-hidden">
        <div class="p-6">
          <h2 class="text-2xl font-bold mb-6 text-center">상권 분석 결과</h2>
  
          <!-- 평가 결과가 있을 때만 결과를 보여줌 -->
          <div v-if="evaluationResult" class="space-y-4 text-lg">
            <p><strong>주 연령층:</strong> <span class="result-value">{{ evaluationResult.age }}</span></p>
            <p><strong>유동인구:</strong> <span class="result-value">{{ evaluationResult.mobility }}</span></p>
            <p><strong>물가:</strong> <span class="result-value">{{ evaluationResult.price }}</span></p>
            <p><strong>분위기:</strong> <span class="result-value">{{ evaluationResult.atmosphere }}</span></p>
          </div>
  
          <!-- 결과가 없을 때 표시 -->
          <div v-else class="text-center text-gray-500">
            <p>평가 결과가 없습니다.</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router'
  import axios from 'axios'
  
  // 평가 결과 저장 변수
  const evaluationResult = ref(null)
  
  // 현재 페이지의 라우트 정보를 가져오기 위해 useRoute 훅 사용
  const route = useRoute()
  
  // 특정 상권의 평가 결과 조회
  const fetchEvaluationResult = async () => {
    try {
      const areaId = route.params.id // URL 파라미터에서 상권 ID 가져오기
      const response = await axios.get(`https://j11d108.p.ssafy.io/api/area-evaluation/area/${areaId}`)
      evaluationResult.value = response.data
      console.log('평가 결과 조회 성공:', response.data)
    } catch (error) {
      console.error('평가 결과 조회 실패:', error)
    }
  }
  
  // 페이지가 로드될 때 자동으로 평가 결과를 조회
  onMounted(() => {
    fetchEvaluationResult()
  })
  </script>
  
  <style scoped>
  .result-value {
    font-weight: 600;
    color: #4a5568;
    background-color: #f7fafc;
    padding: 0.25rem 0.5rem;
    border-radius: 0.375rem;
    display: inline-block;
  }
  
  .text-center {
    text-align: center;
  }
  </style>
  