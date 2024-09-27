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
        {{ time }} 유동인구가 가장 높아요.
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
        유동인구가 이전분기에 비해 {{quarter}}하고 있습니다.
      </div>
      <!--      <ChartComponent />-->
    </div>

    <div class="card mb-3 shadow-sm" id="time">
      <div class="card-body">
        시간대별 유동인구
      </div>
      <div>
        {{ time }} 유동인구가 가장 높아요.
      </div>
      <!--      <ChartComponent />-->
    </div>

    <div class="card mb-3 shadow-sm" id="age">
      <div class="card-body">
        연령별 유동인구
      </div>
      <div>
        {{ age }} 유동인구가 가장 높아요.
      </div>
      <!--      <ChartComponent />-->
    </div>
  </div>
</template>

<script setup>
  import { onMounted, ref } from "vue";
  import { api } from "@/lib/api.js"
  import WeeklyVisitorChart from "@/components/charts/WeeklyVisitorChart.vue";
  import HourlyVisitorChart from "@/components/charts/HourlyVisitorChart.vue";

  const population = ref(0);
  const time = ref("");
  const quarter = ref("");
  const age = ref("");
  const weekData = ref({});
  const hourData = ref({});
  const quarterData = ref({});


  const props = defineProps({
    place: String,
  })


  onMounted( () => {
    api.get(`api/areas/analytic/foot-traffics/daily/${props.place}`) // areaId를 URL에 동적으로 삽입
        .then(response => {
          population.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/day-of-week/${props.place}`) // areaId를 URL에 동적으로 삽입
        .then(response => {
          console.log(response.data)
          weekData.value = response.data;

        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/hour/${props.place}`)
        .then(response => {
          console.log(response.data)
          hourData.value = response.data;
        })
        .catch(err => console.log(err))

    api.get(`api/areas/analytic/foot-traffics/quarterly/${props.place}`)
        .then(response => {
          console.log(response.data)
          quarterData.value = response.data;
        })
        .catch(err => console.log(err))
  })
</script>

<style scoped>


</style>