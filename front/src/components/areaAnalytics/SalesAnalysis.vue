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
        성별{{ GenderSales }}
      </div>
    </div>


    <div class="card shadow-sm p-3 mb-3">
      <div>
          <HorizontalBarChart
          v-if="Object.keys(WeekendSales).length > 0"
          :labels="WeekendSales.labels"
          :data="WeekendSales.data"
          />
      </div>
    </div>

    <div class="card shadow-sm p-3 mb-3">
      <div>
      {{ QuarterlySales.qoq }}
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
import { ref, onMounted, watch } from 'vue'; // Composition API에서 필요한 함수들 임포트
import { api } from "@/lib/api.js"
import WeeklyVisitorChart from "@/components/charts/WeeklyVisitorChart.vue";
import HorizontalBarChart from '@/components/charts/HorizontalBarChart.vue';
import HourlyVisitorChart from "@/components/charts/HourlyVisitorChart.vue";
import QuarterlyVisitorChart from "@/components/charts/QuarterlyVisitorChart.vue";
import GenderGroupChart from "@/components/charts/GenderGroupChart.vue";
import AgeGroupChart from "@/components/charts/GenderGroupChart.vue";
import { getSalesByWeek, getSalesByAge, getSalesByGender, getSalesByWeekend, getSalesByQuarterly} from "@/api/analytic.js"; // API 함수 가져오기


    const WeeklySales = ref("");
    const AgeSales = ref("");
    const GenderSales = ref("");
    const WeekendSales = ref("");
    const QuarterlySales=ref("");
    
    const props = defineProps({
    place : String,
    service : Object
  })

    const loading = ref(true); // 데이터 로딩 상태

    watch(
    () => props.service,
    (newService) => {
      // 컴포넌트가 마운트되면 데이터 호출
      getSalesByWeek(props.place, newService.service_code, (data) => {
        WeeklySales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });
      
      getSalesByAge(props.place, newService.service_code, (data) => {
        AgeSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByGender(props.place, newService.service_code, (data) => {
        GenderSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByWeekend(props.place, newService.service_code, (data) => {
        WeekendSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByQuarterly(props.place, newService.service_code, (data) => {
        QuarterlySales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
        console.log(data)

      }, (error) => {
        console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });
    },
    { immediate: true }
  );

    onMounted(() => {});

</script>

