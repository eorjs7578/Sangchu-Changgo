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
        {{ week }} 유동인구가 가장 높아요.
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
  import {ref} from "vue";

  const population = ref(0);
  const week = ref("");
  const time = ref("");
  const quarter = ref("");
  const age = ref("");
</script>

<style scoped>


</style>