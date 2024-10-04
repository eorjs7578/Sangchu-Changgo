<template>
  <div id="populationAnalysis">
    <div class="card mb-3 shadow-sm" id="day">
      <div class="card-body">
        <h4>일일 평균 유동인구</h4>
        <hr>
        <p>
          일일 평균 유동인구는 <span class="fw-bold text-primary">{{population}}</span>명 입니다.
        </p>
      </div>
    </div>

    <div class="card mb-3 shadow-sm" id="week">
      <div class="card-body">
        <h4>요일별 유동 인구</h4>
        <hr>
        <span class="fw-bold text-primary">{{ weekData.many_people_days_of_week }}요일</span> 유동인구가 가장 높아요.
      </div>

      <WeeklyVisitorChart
          v-if="Object.keys(weekData).length > 0"
          :labels="weekData.labels"
          :data="weekData.data"
      />

    </div>

    <div class="card mb-3 shadow-sm" id="time">
      <div class="card-body">
        <h4>시간대별 유동 인구</h4>
        <hr>
        <span class="fw-bold text-primary">{{ hourData.many_people_days_of_the_hour }}</span> 유동인구가 가장 높아요.
      </div>
    
            <HourlyVisitorChart
            v-if="Object.keys(hourData).length > 0"
            :labels="hourData.labels"
            :data="hourData.data"
            />
    </div>

    <div class="card mb-3 shadow-sm" id="quarter">
      <div class="card-body">
        <h4>분기별 유동 인구</h4>
        <hr>
        <div v-if="quarterData && quarterData.qo_q">
        <!-- qoq 값이 존재하고, "유지" 또는 "상승"을 포함하는 경우 -->
        <p
          v-if="quarterData.qo_q.includes('유지') || quarterData.qo_q.includes('상승')"
        >
          해당 상권의 유동인구가 이전분기에 비해 <span class="text-primary">{{ quarterData.qo_q }}</span>하고 있습니다.
        </p>

        <!-- qoq 값이 존재하고, "하락"을 포함하는 경우 -->
        <p
          v-else-if="quarterData.qo_q.includes('하락')"
        >
          해당 상권의 유동인구가 이전분기에 비해 <span class="text-danger">{{ quarterData.qo_q }}</span>하고 있습니다.
        </p>

        <!-- qoq 값이 특정 문자열인 경우 -->
        <p
          v-else-if="quarterData.qo_q === '올해 없음' "
          class="text-danger"
        >
          이번 년도 유동인구 정보가 없습니다.
        </p>
        <p
          v-else-if="quarterData.qo_q === '현재 없음'"
          class="text-danger"
        >
          현재와 일치하는 동일 분기의 유동인구 정보가 없습니다.
        </p>
      </div>

      <!-- 데이터가 아직 로드되지 않은 경우 로딩 메시지 표시 -->
      <div v-if = "quarterData.length == 0">
        데이터를 불러오는 중입니다...
      </div>
      </div>

            <QuarterlyVisitorChart
                v-if="Object.keys(quarterData).length > 0"
                :labels="quarterData.labels"
                :data="quarterData.data"
            />
    </div>

    <div class="card mb-3 shadow-sm" id="time">
      <div class="card-body">
        <h4>성별별 유동인구</h4>
        <hr>
        <span><span class="fw-bold text-primary">{{ genderData.many_people_gender }}성</span> 유동인구가 약 <span class="fw-bold text-primary">{{ genderPercentage }}%</span> 더 높아요.</span>
      </div>

      <GenderGroupChart :genderData="genderData" />
    </div>

    <div class="card mb-3 shadow-sm" id="age">
      <div class="card-body">
        <h4>연령별 유동인구</h4>
        <hr>
        <span class="fw-bold text-primary">{{ ageData.many_people_days_of_age }}</span> 유동인구가 가장 높아요.
      </div>

      <AgeGroupChart
                v-if="Object.keys(ageData).length > 0"
                :labels="ageData.labels"
                :data="ageData.data"
            />
            
    </div>
  </div>
</template>

<script setup>
  import { onMounted, ref,computed,watch } from "vue";
  import { api } from "@/lib/api.js"
  import WeeklyVisitorChart from "@/components/charts/WeeklyVisitorChart.vue";
  import HourlyVisitorChart from "@/components/charts/HourlyVisitorChart.vue";
  import QuarterlyVisitorChart from "@/components/charts/QuarterlyVisitorChart.vue";
  import GenderGroupChart from "@/components/charts/GenderGroupChart.vue";
  import AgeGroupChart from "@/components/charts/AgeGroupChart.vue";

  const population = ref(0);
  const time = ref("");
  const ageData = ref("");
  const weekData = ref({});
  const hourData = ref({});
  const quarterData = ref({});
  const genderData = ref(0);

  const props = defineProps({
    place: String,
  })

  const genderPercentage = computed(() => {
    if(genderData.value != 0) {
      if(genderData.value.data[0] >= genderData.value.data[1]) {

      return Math.round(((genderData.value.data[0] - genderData.value.data[1] ) / (genderData.value.data[0] + genderData.value.data[1])) * 100)
    }
    return Math.round(((genderData.value.data[1] - genderData.value.data[0] ) / (genderData.value.data[0] + genderData.value.data[1])) * 100)
    }
  })

  watch(
    () => props.place,
    (newPlace => {
    console.log(newPlace);
    api.get(`api/areas/analytic/foot-traffics/daily/${newPlace}`) // areaId를 URL에 동적으로 삽입
        .then(response => {
          population.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/day-of-week/${newPlace}`) // areaId를 URL에 동적으로 삽입
        .then(response => {
          weekData.value = response.data;

        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/hour/${newPlace}`)
        .then(response => {
          hourData.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/quarterly/${newPlace}`)
        .then(response => {
          quarterData.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/gender/${newPlace}`)
        .then(response => {
          genderData.value = response.data;
          console.log(genderData.value)
        })
        .catch(err => console.log(err))
  },
    { immediate: true }
  )
);


  onMounted( () => {
    console.log(props.place);
    api.get(`api/areas/analytic/foot-traffics/daily/${props.place}`) // areaId를 URL에 동적으로 삽입
        .then(response => {
          population.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/day-of-week/${props.place}`) // areaId를 URL에 동적으로 삽입
        .then(response => {
          weekData.value = response.data;

        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/hour/${props.place}`)
        .then(response => {
          hourData.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/age/${props.place}`)
        .then(response => {
          ageData.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/quarterly/${props.place}`)
        .then(response => {
          quarterData.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/gender/${props.place}`)
        .then(response => {
          genderData.value = response.data;
          console.log(genderData.value)
        })
        .catch(err => console.log(err))
  })
</script>

<style scoped>


</style>