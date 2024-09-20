<template>
  <div class="container mt-4">
    <div class="card">
      <div class="card-body">
        <h2 class="card-title mb-4">
          <i class="bi bi-map"></i> 상권분석
        </h2>
        <p class="card-text">상권별 다양의 다양한 정보를 조회할 수 있습니다.</p>

        <div class="mb-4">
          <h3 class="h5">
            <i class="bi bi-geo-alt-fill text-danger"></i> 위치 선택
          </h3>
          <p>분석하고 싶은 상권을 선택해주세요.</p>
          <div class="row g-2">
            <div class="col-md-4">
              <select class="form-select">
                <option>자치구</option>
              </select>
            </div>
            <div class="col-md-4">
              <select class="form-select">
                <option>행정동</option>
              </select>
            </div>
            <div class="col-md-4">
              <select class="form-select">
                <option>상권</option>
              </select>
            </div>
          </div>
        </div>

        <div class="mb-4">
          <h3 class="h5">
            <i class="bi bi-search"></i> 업종 선택
          </h3>
          <p>분석하고 싶은 업종을 선택해주세요.</p>
          <div class="row g-2">
            <div v-for="(category, index) in categories" :key="index" class="col-4 col-md-2">
              <button class="btn btn-outline-secondary w-100" :class="{ 'active': index === 0 }">
                <i :class="category.icon"></i>
                <div class="small mt-1">{{ category.name }}</div>
              </button>
            </div>
          </div>
        </div>

        <div class="row g-2 mb-4">
          <div v-for="(category, index) in two_categories" :key="index" class="col-6 col-md-3">
            <button class="btn btn-outline-secondary w-100" :class="{ 'active': index === 2 }">
              <!-- 아이콘과 텍스트를 동적으로 출력 -->
              <i :class="category.icon"></i> {{ category.name }}
            </button>
          </div>
        </div>

        <button class="btn btn-success w-100 py-3">
          상권분석 시작하기
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const categories = ref([
  { name: '음식점', icon: 'bi bi-cup-hot-fill' },
  { name: '교육', icon: 'bi bi-mortarboard-fill' },
  { name: '레저/오락', icon: 'bi bi-controller' },
  { name: '서비스', icon: 'bi bi-person-circle' },
  { name: '도소매업', icon: 'bi bi-cart-fill' },
  { name: '생활용품', icon: 'bi bi-basket-fill' },
])

const two_categories = ref([
  { name: '음식점', icon: 'bi bi-cup-hot-fill' },
  { name: '교육', icon: 'bi bi-mortarboard-fill' },
  { name: '레저/오락', icon: 'bi bi-controller' },
  { name: '서비스', icon: 'bi bi-person-circle' },
  { name: '도소매업', icon: 'bi bi-cart-fill' },
  { name: '생활용품', icon: 'bi bi-basket-fill' },
  { name: '패션', icon: 'bi bi-shirt' },
  { name: '화장품', icon: 'bi bi-brush' },
  { name: '스포츠', icon: 'bi bi-basketball' },
  { name: '카페', icon: 'bi bi-cup-fill' },
  { name: '편의점', icon: 'bi bi-shop' },
  { name: '기타', icon: 'bi bi-three-dots' }
])
</script>

<style scoped>
@import 'bootstrap/dist/css/bootstrap.min.css';
@import 'bootstrap-icons/font/bootstrap-icons.css';

.btn-outline-secondary {
  border-color: #dee2e6;
}

.btn-outline-secondary:hover,
.btn-outline-secondary.active {
  background-color: #f8f9fa;
  color: #000;
  border-color: #dee2e6;
}
</style>
