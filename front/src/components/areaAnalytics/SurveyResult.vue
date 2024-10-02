<template>
  <div id = "surveyResult">
    <div class="card shadow-sm p-3 mb-3">
      <div class="card-body">

        <h4>상권 분석 결과</h4><br>

        <!-- 평가 결과가 있을 때만 결과를 보여줌 -->
        <div v-if="evaluationResult" class="space-y-4 text-lg">
          <p><strong>주 연령층은</strong> <span class="result-value">{{ evaluationResult.age_group }}</span><strong>입니다.</strong></p>
          <p><strong>유동인구는</strong> <span class="result-value">{{ evaluationResult.foot_traffic }}</span><strong>입니다.</strong></p>
          <p><strong>물가는</strong> <span class="result-value">{{ evaluationResult.nearby_prices }}</span><strong>입니다.</strong></p>
          <p><strong>분위기는</strong> <span class="result-value">{{ evaluationResult.atmosphere }}</span><strong>입니다.</strong></p>
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
import { getStatisticsAreaEvaluation } from '@/api/analytic' // 필요한 경로에 맞춰 import


const areaId = ref('3001491'); // 임시로 이태원 관광특구로 설정
// 평가 결과 저장 변수
const evaluationResult = ref(null)

// 현재 페이지의 라우트 정보를 가져오기 위해 useRoute 훅 사용
const route = useRoute()

// 특정 상권의 평가 결과 조회
const fetchEvaluationResult = async () => {
  try {
    getStatisticsAreaEvaluation(areaId.value, null, (response) => {
      console.log(response)
      evaluationResult.value = response.data
      
      console.log('평가 결과 조회 성공:', response.data)
    }, (error) => {
      console.error('평가 결과 조회 실패:', error)
    })
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
  color: #005eff;
  background-color: #f7fafc;
  padding: 0.25rem 0.5rem;
  border-radius: 0.375rem;
  display: inline-block;
}

.text-center {
  text-align: center;
}
</style>
