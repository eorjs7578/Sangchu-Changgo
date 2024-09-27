<template>
  <div id="storeAnalytics" >
    <div class="card shadow-sm p-3 mb-3">
      <div>
        유사한 주변점포가 {{ SimilarStores }} 개 있어요
      </div>
    </div>
  </div>
</template>


<script>
import { ref, onMounted } from 'vue'; // Composition API에서 필요한 함수들 임포트
import { getNumberOfSimilarStores} from "@/api/analytic.js"; // API 함수 가져오기

export default {
  name: "Dashboard",
  setup() {
    const SimilarStores = ref(null);
    const loading = ref(true); // 데이터 로딩 상태

    onMounted(() => {
      // 컴포넌트가 마운트되면 데이터 호출
      getNumberOfSimilarStores('3110718', 'CS100002', (data) => {
        SimilarStores.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경  

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });
      
    });

    return {   
      loading,
      SimilarStores
    };
  },
};
</script>

