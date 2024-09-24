<template>
  <div class="container-fluid">
    <div class="row bg-success text-white p-3 align-items-center">
      <div class="col-10">
        <h2 class="font-weight-bold">
          {{ place }}
        </h2>
      </div>
      <div class="col-2 text-right">
        <!-- 하트 버튼 -->
        <button class="btn" @click="toggleFavorite">
          {{ favorite ? "♥" : "♡" }}
        </button>
      </div>
    </div>

    <nav class="nav nav-pills nav-fill my-3">
      <a class="nav-item nav-link active" href="#population">인구</a>
      <a class="nav-item nav-link" href="#store-count">점포수</a>
      <a class="nav-item nav-link" href="#analysis">매출분석</a>
      <a class="nav-item nav-link" href="#rent">임대료</a>
      <a class="nav-item nav-link" href="#review">리뷰</a>
    </nav>

    <!-- 각 섹션들 -->
    <div id="population" class="my-5">
      <h3>인구 정보</h3>
      <!-- 인구 정보 차트 -->
      <ChartComponent  chartType="bar" chartData chartOptions />
    </div>

    <div id="store-count" class="my-5">
      <h3>점포수</h3>
      <!-- 점포수 정보 차트 -->
      <ChartComponent chartType="bar" chartData chartOptions />
    </div>

    <div id="analysis" class="my-5">
      <h3>매출분석</h3>
      <!-- 매출분석 정보 차트 -->
      <ChartComponent chartType="line" chartData chartOptions />
    </div>

    <div id="rent" class="my-5">
      <h3>임대료</h3>
      <!-- 임대료 정보 차트 -->
      <ChartComponent
          chartType="line" chartData chartOptions />
    </div>

    <div id="review" class="my-5">
      <h3>리뷰</h3>
      <!-- 리뷰 정보 -->
      <p>리뷰 내용</p>
    </div>

  </div>
</template>

<script setup>
import { ref } from "vue";
import { api } from "@/lib/api";
import ChartComponent from "@/components/charts/ChartComponent.vue";

const props = defineProps({
  place: String
})

const favorite = ref(false);

const rentData = ref({});
const analysisData = ref({});
const populationData = ref({});
const storeCountData = ref({});

function toggleFavorite() {
  favorite.value = !favorite.value;
}

function getData() {
}

</script>

<style>

</style>
