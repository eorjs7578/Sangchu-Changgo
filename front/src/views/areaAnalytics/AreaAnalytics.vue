<template>
  <div class="p-4 max-w-md mx-auto">
    <h1 class="text-2xl font-bold mb-4">상권분석</h1>
    <p class="mb-4">상권별 단위의 다양한 정보를 조회할 수 있습니다.</p>

    <h2 class="text-xl font-semibold mb-2">위치 선택</h2>
    <p class="mb-2">분석하고 싶은 상권을 선택해주세요.</p>
    <LocationSelector v-model="selectedLocation" />

    <h2 class="text-xl font-semibold mt-6 mb-2">업종 선택</h2>
    <p class="mb-2">분석하고 싶은 업종을 선택해주세요.</p>
    <BusinessTypeSelector v-model="selectedBusinessType" />

    <h3 class="text-lg font-semibold mt-6 mb-2">한식음식점</h3>
    <RestaurantList :restaurants="store.restaurants" />

    <button class="w-full bg-green-500 text-white py-3 rounded-md mt-6" @click="startAnalysis">
      상권분석 시작하기
    </button>
  </div>
</template>

<script>
import { ref } from 'vue'
import { useStoreAnalysisStore } from '@/stores/storeAnalysisStore.js'
import LocationSelector from '@/components/areaAnalytics/LocationSelector.vue'
import BusinessTypeSelector from '@/components/areaAnalytics/BusinessTypeSelector.vue'
import RestaurantList from '@/components/areaAnalytics/RestaurantList.vue'

export default {
  name: 'StoreAnalysis',
  components: {
    LocationSelector,
    BusinessTypeSelector,
    RestaurantList
  },
  setup() {
    const store = useStoreAnalysisStore()
    const selectedLocation = ref('')
    const selectedBusinessType = ref('음식점')

    const startAnalysis = () => {
      console.log('Starting analysis...')
      // 분석 로직 구현
    }

    return {
      store,
      selectedLocation,
      selectedBusinessType,
      startAnalysis
    }
  }
}
</script>

<style scoped>
/* 원하는 스타일 추가 */
</style>





<!-- <template>
  <div class="container mt-4">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="d-flex align-items-center mb-3">
          <img src="/placeholder.svg" alt="Map icon" class="me-2" style="height: 40; width: 40">
          <h1 class="mb-0">상권분석</h1>
        </div>
        <p class="mb-4">상권별 단위의 다양한 정보를 조회할 수 있습니다.</p>

        <div class="mb-4">
          <div class="d-flex align-items-center mb-2">
            <img src="/placeholder.svg" alt="Location icon" class="me-2" style="height: 24; width: 24">
            <h2 class="h5 mb-0">위치 선택</h2>
          </div>
          <p>분석하고 싶은 상권을 선택해주세요.</p>
          <div class="d-flex">
            <select class="form-select me-2" v-model="selectedDistrict">
              <option value="">자치구</option>
            </select>
            <select class="form-select me-2" v-model="selectedTown">
              <option value="">행정동</option>
            </select>
            <select class="form-select" v-model="selectedArea">
              <option value="">상권</option>
            </select>
          </div>
        </div>

        <div class="mb-4">
          <div class="d-flex align-items-center mb-2">
            <img src="/placeholder.svg" alt="Business icon" class="me-2" style="height: 24; width: 24">
            <h2 class="h5 mb-0">업종 선택</h2>
          </div>
          <p>분석하고 싶은 업종을 선택해주세요.</p>
          <div class="d-flex flex-wrap">
            <button v-for="(type, index) in businessTypes" :key="index" 
                    class="btn btn-outline-secondary me-2 mb-2" 
                    :class="{ 'active': type.name === '음식점' }"
                    style="height: 24; width: 24">
              <img :src="type.icon" :alt="type.name" class="me-1" style="width: 20px; height: 20px;">
              {{ type.name }}
            </button>
          </div>
        </div>

        <div class="mb-4">
          <h3 class="h6 mb-3">한식음식점</h3>
          <div class="d-flex flex-wrap">
            <button v-for="(restaurant, index) in restaurants" :key="index" 
                    class="btn btn-outline-secondary me-2 mb-2" 
                    :class="{ 'btn-success': index === 1 }">
              {{ restaurant }}
            </button>
          </div>
        </div>

        <button class="btn btn-success w-100 py-3">상권분석 시작하기</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const selectedDistrict = ref('');
const selectedTown = ref('');
const selectedArea = ref('');

const businessTypes = [
  { name: '음식점', icon: '/placeholder.svg' },
  { name: '교육', icon: '/placeholder.svg' },
  { name: '레저/오락', icon: '/placeholder.svg' },
  { name: '서비스', icon: '/placeholder.svg' },
  { name: '도소매업', icon: '/placeholder.svg' },
  { name: '생활용품', icon: '/placeholder.svg' },
];

const restaurants = [
  '한식음식점', '한식음식점', '한식음식점', '한식음식점',
  '한식음식점', '한식음식점', '한식음식점', '한식음식점',
  '한식음식점', '한식음식점', '한식음식점', '한식음식점',
];
</script>

<style scoped>
.btn-outline-secondary {
  border-color: #dee2e6;
}
.btn-outline-secondary:hover, .btn-outline-secondary.active {
  background-color: #f8f9fa;
  border-color: #dee2e6;
  color: #212529;
}
.btn-success {
  background-color: #5cb85c;
  border-color: #5cb85c;
}
</style> -->