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
    <!--  인구 분석  -->
    <PopulationAnalysis />
    <!--  상권 분석  -->
    <StoreAnalytics />

  </div>
</template>

<script setup>
import { ref } from "vue";
import PopulationAnalysis from "@/components/areaAnalytics/PopulationAnalysis.vue";
import StoreAnalytics from "@/components/areaAnalytics/StoreAnalytics.vue";

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
