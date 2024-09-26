<template>
    <div class="min-h-screen bg-gray-100 flex items-center justify-center p-4">
      <div class="w-full max-w-2xl bg-white rounded-lg shadow-xl overflow-hidden">
        <div class="p-6">
          <h2 class="text-2xl font-bold mb-6">상권 분석 결과</h2>
  
          <div v-if="evaluationResult">
            <p><strong>주 연령층은</strong> {{ evaluationResult.age }} 입니다.</p>
            <p><strong>유동인구는</strong> {{ evaluationResult.mobility }} 입니다.</p>
            <p><strong>물가는</strong> {{ evaluationResult.price }} 입니다.</p>
            <p><strong>분위기는</strong> {{ evaluationResult.atmosphere }} 입니다.</p>
          </div>
          <button @click="fetchEvaluationResult" class="px-4 py-2 bg-blue-500 text-white rounded-md mt-4">평가 결과 조회</button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute } from 'vue-router' // Vue Router에서 useRoute 가져오기
  import axios from 'axios'
  
  // 평가 결과 저장 변수
  const evaluationResult = ref(null)
  
  // 현재 페이지의 라우트 정보를 가져오기 위해 useRoute 훅 사용
  const route = useRoute()
  
  // 특정 상권의 평가 결과 조회
  const fetchEvaluationResult = async () => {
    try {
      const areaId = route.params.id // URL 파라미터에서 상권 ID 가져오기
      const response = await axios.get(`/api/area-evaluation/area/${areaId}`)
      evaluationResult.value = response.data
      console.log('평가 결과 조회 성공:', response.data)
    } catch (error) {
      console.error('평가 결과 조회 실패:', error)
    }
  }
  
  // 페이지가 로드될 때 자동으로 평가 결과를 조회할 수 있도록 설정
  onMounted(() => {
    fetchEvaluationResult()
  })
  </script>
  