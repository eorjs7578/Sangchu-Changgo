<template>
  <div id="storeAnalytics" >
    <div class="card shadow-sm p-3 mb-3">
      <div>
        요일별 매출
        <WeeklyVisitorChart
          v-if="Object.keys(WeeklySales).length > 0"
          :labels="WeeklySales.labels"
          :data="WeeklySales.data"
        />
      </div>
    </div>

    <div class="card shadow-sm p-3 mb-3">
      <div>
        나이{{AgeSales}}
        성별{{ GenderSales }}
      </div>
    </div>

    <div class="card shadow-sm p-3 mb-3">
      <div>
        주중, 주말
        {{ WeekendSales }}
      </div>
    </div>

    <div class="card shadow-sm p-3 mb-3">
      <div>      
       
        해당업종의 매출이 이전분기에 비해 {{ QuarterlySales.qoq }}하고 있습니다.
        <QuarterlyVisitorChart
                v-if="Object.keys(QuarterlySales ).length > 0"
                :labels="QuarterlySales.labels"
                :data="QuarterlySales.data"
            />
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted } from 'vue'; // Composition API에서 필요한 함수들 임포트
import { api } from "@/lib/api.js"
import WeeklyVisitorChart from "@/components/charts/WeeklyVisitorChart.vue";
import HourlyVisitorChart from "@/components/charts/HourlyVisitorChart.vue";
import QuarterlyVisitorChart from "@/components/charts/QuarterlyVisitorChart.vue";
import GenderGroupChart from "@/components/charts/GenderGroupChart.vue";
import { getSalesByWeek, getSalesByAge, getSalesByGender, getSalesByWeekend, getSalesByQuarterly} from "@/api/analytic.js"; // API 함수 가져오기


    const WeeklySales = ref("");
    const AgeSales = ref("");
    const GenderSales = ref("");
    const WeekendSales = ref("");
    const QuarterlySales=ref("");
    
    const props = defineProps({
    place: String,
  })

    const loading = ref(true); // 데이터 로딩 상태

    onMounted(() => {
      // 컴포넌트가 마운트되면 데이터 호출
      getSalesByWeek(props.place, 'CS100002', (data) => {
        WeeklySales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });
      
      getSalesByAge(props.place, 'CS100002', (data) => {
        AgeSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByGender(props.place, 'CS100002', (data) => {
        GenderSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByWeekend(props.place, 'CS100002', (data) => {
        WeekendSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByQuarterly(props.place, 'CS100002', (data) => {
        QuarterlySales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });
    });

</script>

