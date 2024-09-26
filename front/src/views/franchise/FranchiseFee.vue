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
                  <h3 class="mb-4">
                    창업하시려는 <span class="text-primary">위치</span>를 입력해 주세요
                  </h3>
                  <div class="row"> <!-- 간격 조정 -->
                    <div class="col-md-6 dropdown-container">
                      <div class="input-group">
                        <span class="input-group-text">
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                          </svg>
                        </span>
                        <input type="text" class="form-control" v-model="gu" placeholder="구" @focus="showGuDropdown = true" @blur="hideGuDropdown">
                      </div>
                      <div v-if="showGuDropdown" class="dropdown-menu show custom-dropdown">
                        <a v-for="guItem in gus" :key="guItem.code" class="dropdown-item" @click="selectGu(guItem)">
                          {{ guItem.name }}
                        </a>
                      </div>
                    </div>
                    <div class="col-md-6 dropdown-container">
                      <div class="input-group">
                        <span class="input-group-text">
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                          </svg>
                        </span>
                        <input type="text" class="form-control" v-model="dong" placeholder="동" @focus="showDongDropdown = true" @blur="hideDongDropdown">
                      </div>
                      <div v-if="showDongDropdown" class="dropdown-menu show custom-dropdown">
                        <a v-for="dongItem in dongs" :key="dongItem.code" class="dropdown-item"  @mousedown="selectDong(dongItem)">
                          {{ dongItem.name }}
                        </a>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="mb-4"> <!-- 간격 조정 -->
                  <h3>원하는 <span class="text-primary">프랜차이즈</span>를 선택해주세요</h3>
                  <div class="row"> <!-- 간격 조정 -->
                    <div class="col-md-6 dropdown-container">
                      <div class="input-group">
                        <span class="input-group-text">
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                          </svg>
                        </span>
                        <input type="text" class="form-control" v-model="category" placeholder="분류" @focus="showCategoryDropdown = true" @blur="hideCategoryDropdown">
                      </div>
                      <div v-if="showCategoryDropdown" class="dropdown-menu show custom-dropdown">
                        <a v-for="category in categories" class="dropdown-item" @click="selectCategory(category)">
                          {{ category.type }}
                        </a>
                      </div>
                    </div>
                    <div class="col-md-6 dropdown-container">
                      <div class="input-group">
                        <span class="input-group-text">
                          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
                              <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
                          </svg>
                        </span>
                        <input type="text" class="form-control" v-model="franchise.name" placeholder="프랜차이즈 선택" @focus="showFranchiseDropdown = true" @blur="hideFranchiseDropdown">
                      </div>
                      <div v-if="showFranchiseDropdown" class="dropdown-menu show custom-dropdown">
                        <a v-for="franchise in franchises" class="dropdown-item" @click="selectFranchise(franchise)">
                          {{ franchise.name }}
                        </a>
                      </div>
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
                <FranchiseInfoCard :franchise="myFranchise"/>
              </div>
              <h6 class="mb-3">다른 프랜차이즈는 어때요?</h6>
              <div class="row g-3">
                <div class="row overflow-x">
                  <div class="col-auto" v-for="(franchise, index) in otherFranchises" :key="index">
                    <div class="card" >
                      <FranchiseInfoCard :franchise="franchise" />
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
import { ref, onMounted } from 'vue'
import { getGu, getDong } from "@/api/region.js";
import { getType, getFranchisesByType} from '@/api/franchise.js'
import FranchiseInfoCard from '@/components/franchise/FranchiseInfoCard.vue';

const gus = ref([])
const dongs = ref([])
const categories = ref([])
const franchises = ref([])
const showGuDropdown = ref(false)
const showDongDropdown = ref(false)
const showCategoryDropdown = ref(false)
const showFranchiseDropdown = ref(false)

const getGuInfos = () => {
  getGu(
    ({data}) => {
      console.log(data)
      gus.value = data
      
    },
    (error) => {
      console.log(error)
    }
  )
}

const getTypes = () => {
  getType(
    ({data}) => {
      console.log(data)
      categories.value = data
    },
    (error) => {
      console.log(error)
    }
  )
}

const selectCategory = (payload) => {
  category.value = payload.type
  getFranchisesByType(
    payload.type,
    ({data}) => {
      console.log(data)
      franchises.value = data
      showCategoryDropdown.value = false
    },
    (error) => {
      console.log(error)
    }
  )
}

const selectFranchise = (payload) => {
  franchise.value = payload
  showFranchiseDropdown.value = false
}

const selectGu = (payload) => {
  gu.value = payload.name
  getDong(
    payload.code,
    ({data}) => {
      console.log(data)
      dongs.value = data
      showGuDropdown.value = false
    },
    (error) => {
      console.log(error)
    }
  )
}

const selectDong = (dongItem) => {
  dong.value = dongItem.name
  showDongDropdown.value = false
}

const hideGuDropdown = () => {
  setTimeout(() => {
    showGuDropdown.value = false
  }, 200)
}

const hideDongDropdown = () => {
  setTimeout(() => {
    showDongDropdown.value = false
  }, 200)
}

const hideCategoryDropdown = () => {
  setTimeout(() => {
    showCategoryDropdown.value = false
  }, 200)
}

const hideFranchiseDropdown = () => {
  setTimeout(() => {
    showFranchiseDropdown.value = false
  }, 200)
}

onMounted(() => {
  getGuInfos(),
  getTypes()
})

const currentStep = ref(1)
const location = ref('')
const category = ref('')
const franchise = ref('')
const storeSize = ref('')
const selectedFloor = ref('')
const gu = ref('')
const dong = ref('')

const myFranchise = ref({
    gu : gu,
    dong : dong,
    name: '12412415555524',
    cost: 10000000,
    costs: [
      { name: '가맹비', amount: 15000000 },
      { name: '보증금', amount: 10000000 },
      { name: '교육비', amount: 5000000 },
      { name: '인테리어 비용', amount: 18000000 },
      { name: '기타비용', amount: 2000000 },
    ]
  },
)

const otherFranchises = ref([
  {
    gu : gu,
    dong : dong,
    name: '12412415555524',
    cost: 10000000,
    costs: [
      { name: '가맹비', amount: 15000000 },
      { name: '보증금', amount: 10000000 },
      { name: '교육비', amount: 5000000 },
      { name: '인테리어 비용', amount: 18000000 },
      { name: '기타비용', amount: 2000000 },
    ]
  },
  {
    gu : gu,
    dong : dong,
    name : 'asdffsda',
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
    gu : gu,
    dong : dong,
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
  category.value = ''
  franchise.value = ''
  storeSize.value = ''
  selectedFloor.value = ''
  gu.value = ''
  dong.value = ''
}
</script>

<style scoped>

.overflow-auto {
  overflow-x: auto; /* 가로 스크롤 허용 */
  white-space: nowrap; /* 요소들이 한 줄에 나열되도록 */
}

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

.dropdown-container {
  position: relative;
}

.custom-dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  z-index: 1000;
  max-height: 200px;
  overflow-y: auto;
  width: 100%;
  margin-top: 0;
  padding: 0;
  border: 1px solid rgba(0, 0, 0, 0.15);
  border-radius: 0.25rem;
  background-color: #fff;
  box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.175);
}

.custom-dropdown .dropdown-item {
  padding: 0.5rem 1rem;
  clear: both;
  font-weight: 400;
  color: #212529;
  text-align: inherit;
  white-space: nowrap;
  background-color: transparent;
  border: 0;
}

.custom-dropdown .dropdown-item:hover,
.custom-dropdown .dropdown-item:focus {
  color: #16181b;
  text-decoration: none;
  background-color: #f8f9fa;
}

.input-group {
  position: relative;
  z-index: 1;
}
</style>
