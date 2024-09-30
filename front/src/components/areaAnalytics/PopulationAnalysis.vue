<template>
  <div id="population">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        유동 인구
      </div>
    </div>

    <div class="card p-3 mb-3 shadow-sm" id="day">
      <div class="card-body">
        <p>
          일일 평균 유동인구
        </p>
        <p>
          일일 평균 유동인구는 {{population}}명 입니다.
        </p>
      </div>
    </div>

    <div class="card mb-3 shadow-sm" id="week">
      <div class="card-body">
        요일별 유동 인구
      </div>
      <div>
        {{ weekData.many_people_days_of_week }}요일 유동인구가 가장 높아요.
      </div>

      <WeeklyVisitorChart
          v-if="Object.keys(weekData).length > 0"
          :labels="weekData.labels"
          :data="weekData.data"
      />

    </div>

    <div class="card mb-3 shadow-sm" id="time">
      <div class="card-body">
        시간대별 유동인구
      </div>
      <div>
        {{ hourData.many_people_days_of_the_hour }} 유동인구가 가장 높아요.
      </div>
            <HourlyVisitorChart
            v-if="Object.keys(hourData).length > 0"
            :labels="hourData.labels"
            :data="hourData.data"
            />
    </div>

    <div class="card mb-3 shadow-sm" id="quarter">
      <div class="card-body">
        분기별 평균 유동인구
      </div>
      <div>
        유동인구가 이전분기에 비해 {{ quarterData.qo_q }}하고 있습니다.
      </div>
            <QuarterlyVisitorChart
                v-if="Object.keys(quarterData).length > 0"
                :labels="quarterData.labels"
                :data="quarterData.data"
            />
    </div>

    <div class="card mb-3 shadow-sm" id="time">
      <div class="card-body">
        유동인구 성별
      </div>
      <div>
        {{ genderData.many_people_gender }}성 유동인구가 가장 높아요.
      </div>

    </div>

    <div class="card mb-3 shadow-sm" id="age">
      <div class="card-body">
        연령별 유동인구
      </div>
      <div>
        {{ ageData.many_people_days_of_age }} 유동인구가 가장 높아요.
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
  import { onMounted, ref } from "vue";
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
  const genderData = ref({});

  const props = defineProps({
    place: String,
  })


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