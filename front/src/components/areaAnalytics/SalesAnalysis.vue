<template>
  <div id="salesAnalysis" >
    <div class="card shadow-sm mb-3">
      <div class="card-body">
        <h4>요일별 매출</h4>
        <hr>
        <div v-if="WeeklySales.many_money_days_of_week">
          <span class="fw-bold text-primary">{{ WeeklySales.many_money_days_of_week }}요일</span> 매출이 가장 높아요
        </div>
        <div v-else>
          <span class="fw-bold text-danger">요일별 매출 정보가 없습니다.</span> 
        </div>
      </div>
      <div v-if="WeeklySales.many_money_days_of_week">        
        <WeeklySalesChart

          v-if="Object.keys(WeeklySales).length > 0"
          :labels="WeeklySales.labels"
          :data="WeeklySales.data"
        />
      </div>
    </div>

    <div class="card mb-3 shadow-sm" id="time">
      <div class="card-body">
        <h4>성별별 매출</h4>
        <hr>
        <div v-if="!isNaN(genderPercentage)">
          <span> 전체 매출의 약 <span class="fw-bold text-primary">{{ genderPercentage }}%</span>가 <span class="fw-bold text-primary">{{ GenderSales.many_sale_gender }}성</span> 매출입니다.</span>
        </div>
        <div v-else>
          <span class="fw-bold text-danger">성별별 매출 정보가 없습니다.</span> 
        </div>
      </div>
      <div>
        
      </div>
      <GenderGroupChart v-if="!isNaN(genderPercentage)" :genderData="GenderSales" />
    </div>


    <div class="card shadow-sm mb-3">
      <div class="card-body">
        <h4>주중, 주말별 매출</h4>
        <hr>
        <div v-if="WeekendSales == ''">
          <span class="fw-bold text-danger">주중, 주말별 매출 정보가 없습니다.</span> 
        </div>
      </div>
      <div>

      </div>
      <div v-if="WeekendSales.labels">
          <HorizontalBarChart
          v-if="Object.keys(WeekendSales).length > 0"
          :labels="WeekendSales.labels"
          :data="WeekendSales.data"
          />
      </div>
    </div>

    <div class="card shadow-sm mb-3">
      <div class="card-body">
        <h4>연령별 매출</h4>
        <hr>
        <div v-if="AgeSales.data && AgeSales.data.length === 0">
          <span class="fw-bold text-danger">연령별 매출 정보가 없습니다.</span> 
        </div>
        <div v-else-if="AgeSales.many_sale_age">
          <span class="fw-bold text-primary">{{ AgeSales.many_sale_age }}</span> 매출이 가장 높아요
        </div>
        <div v-else>
          <span class="fw-bold text-danger">연령별 매출 정보가 없습니다.</span> 
        </div>
        
      </div>
      
      <div v-if="AgeSales.many_sale_age">
          <AgeGroupChart
                v-if="Object.keys(AgeSales).length > 0"
                :labels="AgeSales.labels"
                :data="AgeSales.data"
            />
      </div>
    </div>

    <div class="card shadow-sm mb-3">
      <div class="card-body">
        <h4>분기별 매출</h4>
        <hr>
        <div v-if="QuarterlySales && QuarterlySales.qoq">
        <!-- qoq 값이 존재하고, "유지" 또는 "상승"을 포함하는 경우 -->
        <p
          v-if="QuarterlySales.qoq.includes('유지') || QuarterlySales.qoq.includes('상승')"
        >
          해당 업종의 매출이 작년 해당 분기에 비해 <span class="fw-bold text-primary">{{ QuarterlySales.qoq }}</span>하고 있습니다.
        </p>

        <!-- qoq 값이 존재하고, "하락"을 포함하는 경우 -->
        <p
          v-else-if="QuarterlySales.qoq.includes('하락')"
        >
          해당 업종의 매출이 작년 해당 분기에 비해 <span class="fw-bold text-danger">{{ QuarterlySales.qoq }}</span>하고 있습니다.
        </p>

        <!-- qoq 값이 특정 문자열인 경우 -->
        <p
          v-else-if="QuarterlySales.qoq === '올해 없음' "
          class="fw-bold text-danger"
        >
          이번 년도 매출 정보가 없습니다.
        </p>
        <p
          v-else-if="QuarterlySales.qoq === '현재 없음'"
          class="fw-bold text-danger"
        >
          현재와 일치하는 동일 분기의 매출 정보가 없습니다.
        </p>
      </div>

      <!-- 데이터가 아직 로드되지 않은 경우 로딩 메시지 표시 -->
      <div v-if = "QuarterlySales.length == 0">
        데이터를 불러오는 중입니다...
      </div>
      </div>
      <div v-if="!(QuarterlySales.qoq === '올해 없음' || QuarterlySales.qoq === '현재 없음')">
      
        <QuarterlySalesChart
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
import WeeklySalesChart from "@/components/charts/WeeklySalesChart.vue";
import HorizontalBarChart from '@/components/charts/HorizontalBarChart.vue';
import HourlyVisitorChart from "@/components/charts/HourlyVisitorChart.vue";
import QuarterlySalesChart from "@/components/charts/QuarterlySalesChart.vue";
import GenderGroupChart from "@/components/charts/GenderGroupChart.vue";
import AgeGroupChart from "@/components/charts/AgeGroupChart.vue";
import { getSalesByWeek, getSalesByAge, getSalesByGender, getSalesByWeekend, getSalesByQuarterly} from "@/api/analytic.js"; // API 함수 가져오기


    const WeeklySales = ref("");
    const AgeSales = ref("");
    const GenderSales = ref("");
    const WeekendSales = ref("");
    const QuarterlySales=ref({});
    
    const props = defineProps({
    place : String,
    service : Object
  })

    const loading = ref(true); // 데이터 로딩 상태

    const genderPercentage = computed(() => {
    if(GenderSales.value != 0) {
      if(GenderSales.value.data[0] >= GenderSales.value.data[1]) {

      return Math.round((GenderSales.value.data[0] / (GenderSales.value.data[0] + GenderSales.value.data[1])) * 100)
    }
    return Math.round((GenderSales.value.data[1] / (GenderSales.value.data[0] + GenderSales.value.data[1])) * 100)
    }
  })

    watch(
    () => props.service,
    (newService) => {
      // 컴포넌트가 마운트되면 데이터 호출
      getSalesByWeek(props.place, newService.service_code, (data) => {
        WeeklySales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
      }, (error) => {
        // console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });
      
      getSalesByAge(props.place, newService.service_code, (data) => {
        AgeSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 

      }, (error) => {
        // console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByGender(props.place, newService.service_code, (data) => {
        GenderSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 

      }, (error) => {
        // console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByWeekend(props.place, newService.service_code, (data) => {
        WeekendSales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 

      }, (error) => {
        // console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });

      getSalesByQuarterly(props.place, newService.service_code, (data) => {
        QuarterlySales.value = data.data; // 성공 시 데이터 설정
        loading.value = false; // 로딩 상태 변경 
      }, (error) => {
        // console.error("월평균 매출 호출 오류:", error); // 실패 시 오류 출력
        loading.value = false; // 로딩 상태 변경
      });
    },
    { immediate: true }
  );

    onMounted(() => {});

</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap');

* {
    font-family: 'Noto Sans KR', sans-serif;
}

.button {
    background-color: #28a745;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}

.button:hover {
    background-color: #218838;
}

.nav-link {
    color: #333;
    font-size: 1.1rem;
    font-weight: 500;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.nav-link:hover {
    background-color: #e9ecef;
    color: #28a745;
    border-radius: 5px;
}
</style>
