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

    <div class="card mb-3 shadow-sm" id="time">
      <div class="card-body">
        성별별 매출
      </div>
      <div>
        <span>{{ GenderSales.many_sale_gender }}성 매출이 약 {{ genderPercentage }}% 더 높아요.</span>
      </div>
      <GenderGroupChart :genderData="GenderSales" />
    </div>


    <div class="card shadow-sm p-3 mb-3">
      <h4>주중, 주말별 매출</h4>
      <div>
          <HorizontalBarChart
          v-if="Object.keys(WeekendSales).length > 0"
          :labels="WeekendSales.labels"
          :data="WeekendSales.data"
          />
      </div>
    </div>

    <div class="card shadow-sm p-3 mb-3">
      <h3>연령별 매출</h3>
      {{ AgeSales.many_sale_age }} 매출이 가장 높아요
      <div>
          <AgeGroupChart
                v-if="Object.keys(AgeSales).length > 0"
                :labels="AgeSales.labels"
                :data="AgeSales.data"
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
import { ref, onMounted, watch, computed } from 'vue'; // Composition API에서 필요한 함수들 임포트
import { api } from "@/lib/api.js"
import WeeklyVisitorChart from "@/components/charts/WeeklyVisitorChart.vue";
import HorizontalBarChart from '@/components/charts/HorizontalBarChart.vue';
import HourlyVisitorChart from "@/components/charts/HourlyVisitorChart.vue";
import QuarterlyVisitorChart from "@/components/charts/QuarterlyVisitorChart.vue";
import GenderGroupChart from "@/components/charts/GenderGroupChart.vue";
import AgeGroupChart from "@/components/charts/AgeGroupChart.vue";
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

    const genderPercentage = computed(() => {
    if(GenderSales.value != 0) {
      if(GenderSales.value.data[0] >= GenderSales.value.data[1]) {

      return Math.round(((GenderSales.value.data[0] - GenderSales.value.data[1] ) / (GenderSales.value.data[0] + GenderSales.value.data[1])) * 100)
    }
    return Math.round(((GenderSales.value.data[1] - GenderSales.value.data[0] ) / (GenderSales.value.data[0] + GenderSales.value.data[1])) * 100)
    }
  })

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

