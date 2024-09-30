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
        <span>{{ genderData.many_people_gender }}성 유동인구가 약 {{ genderPercentage }}% 더 높아요.</span>
      </div>
      <GenderGroupChart :genderData="genderData" />
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
  import { onMounted, ref,computed,watch } from "vue";
  import { api } from "@/lib/api.js"
  import WeeklyVisitorChart from "@/components/charts/WeeklyVisitorChart.vue";
  import HourlyVisitorChart from "@/components/charts/HourlyVisitorChart.vue";
  import QuarterlyVisitorChart from "@/components/charts/QuarterlyVisitorChart.vue";
  import GenderGroupChart from "@/components/charts/GenderGroupChart.vue";

  const population = ref(0);
  const time = ref("");
  const age = ref("");
  const weekData = ref({});
  const hourData = ref({});
  const quarterData = ref({});
  const genderData = ref(0);

  const props = defineProps({
    place: String,
  })

  const genderPercentage = computed(() => {
    if(genderData.value != 0) {
      if(genderData.value.many_people_gender == '남') {
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