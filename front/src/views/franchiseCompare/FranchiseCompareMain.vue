<template>
  <div class="modal fade" id="koreanModal" tabindex="-1" aria-labelledby="koreanModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered custom-modal-width custom-modal-height">
      <div class="modal-content">
        <div class="modal-header bg-success text-white">
          <h5 class="modal-title" id="koreanModalLabel">창업 예상 비용 확인</h5>
          <button @click="setParamsDefault" type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <transition name="fade" mode="out-in">
            <!-- Step 1 -->
            <div v-if="currentStep === 1" key="step1" class="h-100 d-flex flex-column justify-content-between">
                <div class="mb-4"> <!-- 간격 조정 -->
                  <label for="location" class="form-label">창업하시려는 위치를 입력해 주세요</label>
                  <div class="input-group">
                    <span class="input-group-text"><i class="bi bi-search"></i></span>
                    <input type="text" class="form-control" id="location" v-model="location" placeholder="구 / 동 검색">
                  </div>
                </div>
                <div class="mb-4"> <!-- 간격 조정 -->
                  <label class="form-label">원하는 프랜차이즈를 선택해주세요</label>
                  <div class="row"> <!-- 간격 조정 -->
                    <div class="col-md-6">
                      <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-search"></i></span>
                        <input type="text" class="form-control" v-model="mainCategory" placeholder="대분류">
                      </div>
                    </div>
                    <div class="col-md-6">
                      <div class="input-group">
                        <span class="input-group-text"><i class="bi bi-search"></i></span>
                        <input type="text" class="form-control" v-model="subCategory" placeholder="중분류">
                      </div>
                    </div>
                  </div>
                  <div class="mt-4">
                    <div class="input-group">
                      <span class="input-group-text"><i class="bi bi-search"></i></span>
                      <input type="text" class="form-control" v-model="franchise" placeholder="프랜차이즈 선택">
                    </div>
                  </div>
                </div>
                <button @click="nextStep" class="btn btn-success w-100">다음</button>
            </div>

            <!-- Step 2 -->
            <div v-else-if="currentStep === 2" key="step2" class="h-100 d-flex flex-column justify-content-between">
              <div>
                <h6 class="mb-4">매장크기를 선택해 주세요.</h6>
              <div class="row mb-4">
                <div class="col-6">
                  <div class="card" :class="{ 'border-primary': storeSize === 'small' }" @click="storeSize = 'small'">
                    <div class="card-body text-center">
                      <i class="bi bi-shop fs-1"></i>
                      <p class="mt-2">1평 이하</p>
                      <h6>소형</h6>
                    </div>
                  </div>
                </div>
                <div class="col-6">
                  <div class="card" :class="{ 'border-primary': storeSize === 'large' }" @click="storeSize = 'large'">
                    <div class="card-body text-center">
                      <i class="bi bi-building fs-1"></i>
                      <p class="mt-2">1평 이상</p>
                      <h6>중대형</h6>
                    </div>
                  </div>
                </div>
              </div>
              </div>
              
              <div>
                <h6 class="mb-4">층수를 선택해 주세요.</h6>
                  <div class="row mb-4">
                    <div class="col-4" v-for="floor in ['지하', '1층', '1층 이상']" :key="floor">
                      <div class="card" :class="{ 'border-primary': selectedFloor === floor }" @click="selectedFloor = floor">
                        <div class="card-body text-center">
                          <h6>{{ floor }}</h6>
                        </div>
                      </div>
                    </div>
                  </div>
              </div>
              
              <div class="d-flex justify-content-between">
                <button @click="prevStep" class="btn btn-secondary w-100 me-2">이전</button>
                <button @click="showCostBreakdown" class="btn btn-success w-100 ms-2">비용 확인</button>
              </div>
            </div>

            <!-- Step 3 -->
            <div v-else-if="currentStep === 3" key="step3">
              <div class="card mb-4">
                <div class="card-body">
                  <h6 class="card-subtitle mb-2 text-muted">{{ location }} {{ franchise }}</h6>
                  <h4 class="card-title">예상 창업 비용은 <span class="text-primary">{{ totalCost.toLocaleString() }}원</span> 입니다.</h4>
                  <table class="table table-borderless">
                    <tbody>
                      <tr v-for="(cost, index) in costs" :key="index">
                        <td>{{ cost.name }}</td>
                        <td class="text-end">{{ cost.amount.toLocaleString() }}원</td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </div>
              <h6 class="mb-3">다른 프랜차이즈는 어때요?</h6>
              <div class="row g-3">
                <div class="col-md-6" v-for="(franchise, index) in otherFranchises" :key="index">
                  <div class="card">
                    <div class="card-body">
                      <h6 class="card-title">{{ franchise.name }}</h6>
                      <p class="card-text">{{ franchise.cost.toLocaleString() }}원</p>
                      <table class="table table-sm table-borderless">
                        <tbody>
                          <tr v-for="(cost, costIndex) in franchise.costs" :key="costIndex">
                            <td>{{ cost.name }}</td>
                            <td class="text-end">{{ cost.amount.toLocaleString() }}원</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
              <button @click="prevStep" class="btn btn-secondary w-100 mt-4">이전</button>
            </div>
          </transition>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

const currentStep = ref(1)
const location = ref('')
const mainCategory = ref('')
const subCategory = ref('')
const franchise = ref('')
const storeSize = ref('')
const selectedFloor = ref('')

const costs = ref([
  { name: '가맹비', amount: 10000000 },
  { name: '보증금', amount: 10000000 },
  { name: '교육비', amount: 5000000 },
  { name: '인테리어 비용', amount: 15000000 },
  { name: '기타비용', amount: 2110000 },
])

const totalCost = computed(() => costs.value.reduce((sum, cost) => sum + cost.amount, 0))

const otherFranchises = ref([
  {
    name: '5천만원',
    cost: 50000000,
    costs: [
      { name: '가맹비', amount: 15000000 },
      { name: '보증금', amount: 10000000 },
      { name: '교육비', amount: 5000000 },
      { name: '인테리어 비용', amount: 18000000 },
      { name: '기타비용', amount: 2000000 },
    ]
  },
  {
    name: '5천만원',
    cost: 50000000,
    costs: [
      { name: '가맹비', amount: 15000000 },
      { name: '보증금', amount: 10000000 },
      { name: '교육비', amount: 5000000 },
      { name: '인테리어 비용', amount: 18000000 },
      { name: '기타비용', amount: 2000000 },
    ]
  }
])


const nextStep = () => {
  currentStep.value++
}

const prevStep = () => {
  currentStep.value--
}

const showCostBreakdown = () => {
  currentStep.value = 3
}

const submitForm = () => {
  console.log('Form submitted', {
    location: location.value,
    mainCategory: mainCategory.value,
    subCategory: subCategory.value,
    franchise: franchise.value,
    storeSize: storeSize.value,
    selectedFloor: selectedFloor.value
  })
}

const setParamsDefault = () => {
  currentStep.value = 1
  location.value = ''
  mainCategory.value = ''
  subCategory.value = ''
  franchise.value = ''
  storeSize.value = ''
  selectedFloor.value = ''
}
</script>

<style scoped>
.custom-modal-width {
  max-width: 70vw;
  width: 70%;
}

.custom-modal-height {
  height: 90vh;
}

.modal-content {
  height: 100%;
}

.modal-body {
  overflow-y: auto;
}

.mb-4 {
  margin-bottom: 1.5rem !important; /* 요소 간 간격 추가 */
}

.g-3 {
  gap: 1.5rem !important; /* 요소 간격 */
}

.w-100 {
  width: 100%;
}

@media (max-width: 768px) {
  .custom-modal-width {
    max-width: 90vw;
    width: 90%;
  }
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.card {
  cursor: pointer;
  transition: all 0.3s ease;
}

.card:hover {
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

.border-primary {
  border-color: #28a745 !important;
  box-shadow: 0 0 0 0.2rem rgba(40, 167, 69, 0.25);
}
</style>
