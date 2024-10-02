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
                        <input type="text" class="form-control" v-model="gu.name" placeholder="구" @focus="showGuDropdown = true" @blur="hideGuDropdown">
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
                        <input type="text" class="form-control" v-model="dong.name" placeholder="동" @focus="showDongDropdown = true" @blur="hideDongDropdown">
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
                <h3 class="mb-4"><span class="text-primary">매장 크기</span>를 선택해 주세요.</h3>
              <div class="row mb-4">
                <div class="col-6">
                  <div class="card" :class="{ 'border-primary': storeSize === 'small' }" @click="storeSize = 'small'">
                    <div class="card-body text-center">
                      <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-shop" viewBox="0 0 16 16">
                        <path d="M2.97 1.35A1 1 0 0 1 3.73 1h8.54a1 1 0 0 1 .76.35l2.609 3.044A1.5 1.5 0 0 1 16 5.37v.255a2.375 2.375 0 0 1-4.25 1.458A2.37 2.37 0 0 1 9.875 8 2.37 2.37 0 0 1 8 7.083 2.37 2.37 0 0 1 6.125 8a2.37 2.37 0 0 1-1.875-.917A2.375 2.375 0 0 1 0 5.625V5.37a1.5 1.5 0 0 1 .361-.976zm1.78 4.275a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0 1.375 1.375 0 1 0 2.75 0V5.37a.5.5 0 0 0-.12-.325L12.27 2H3.73L1.12 5.045A.5.5 0 0 0 1 5.37v.255a1.375 1.375 0 0 0 2.75 0 .5.5 0 0 1 1 0M1.5 8.5A.5.5 0 0 1 2 9v6h1v-5a1 1 0 0 1 1-1h3a1 1 0 0 1 1 1v5h6V9a.5.5 0 0 1 1 0v6h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1V9a.5.5 0 0 1 .5-.5M4 15h3v-5H4zm5-5a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3a1 1 0 0 1-1 1h-2a1 1 0 0 1-1-1zm3 0h-2v3h2z"/>
                      </svg>
                      <p class="mt-2">약 10평</p>
                      <h6>소형</h6>
                    </div>
                  </div>
                </div>
                <div class="col-6">
                  <div class="card" :class="{ 'border-primary': storeSize === 'large' }" @click="storeSize = 'large'">
                    <div class="card-body text-center">
                      <svg xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-building" viewBox="0 0 16 16">
                        <path d="M4 2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5zm3 0a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5zm3.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zM4 5.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5zM7.5 5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm2.5.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5zM4.5 8a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5zm2.5.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.5-.5zm3.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5z"/>
                        <path d="M2 1a1 1 0 0 1 1-1h10a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H3a1 1 0 0 1-1-1zm11 0H3v14h3v-2.5a.5.5 0 0 1 .5-.5h3a.5.5 0 0 1 .5.5V15h3z"/>
                      </svg>
                      <p class="mt-2">약 20평</p>
                      <h6>중대형</h6>
                    </div>
                  </div>
                </div>
              </div>
              </div>
              
              <div>
                <h3 class="mb-4"><span class="text-primary">층수</span>를 선택해 주세요.</h3>
                <div class="row mb-4">
                <div class="col-6">
                  <div class="card" :class="{ 'border-primary': selectedFloor === '1' }" @click="selectedFloor = '1'">
                    <div class="card-body text-center">
                      <h6>1층</h6>
                    </div>
                  </div>
                </div>
                <div class="col-6">
                  <div class="card" :class="{ 'border-primary': selectedFloor === 'other' }" @click="selectedFloor = 'other'">
                    <div class="card-body text-center">
                      <h6>1층 외</h6>
                    </div>
                  </div>
                </div>
              </div>
              </div>
              
              <div class="d-flex justify-content-between h-auto">
                <button @click="prevStep" class="btn btn-secondary w-100 me-2">이전</button>
                <button @click="showCostBreakdown" class="btn btn-success w-100 ms-2">비용 확인</button>
              </div>
            </div>

            <!-- Step 3 -->
            <div v-else-if="currentStep === 3" key="step3" class = "h-100 d-flex flex-column justify-content-between">
              <div class="card mb-4">
                <FranchiseInfoCard :franchise="myFranchise"/>
              </div>
              <!-- <h6 class="mb-3">다른 프랜차이즈는 어때요?</h6>
              <div class="row g-3">
                <div class="row overflow-x">
                  <div class="col-auto" v-for="(franchise, index) in otherFranchises" :key="index">
                    <div class="card" >
                      <FranchiseInfoCard :franchise="franchise" />
                    </div>
                  </div>
                </div>
              </div> -->
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
import { getGu, getDong, getDongRentFee } from "@/api/region.js";
import { getType, getFranchisesByType, getFranchisesFee} from '@/api/franchise.js'
import FranchiseInfoCard from '@/components/franchise/FranchiseInfoCard.vue';

const gus = ref([])
const dongs = ref([])
const categories = ref([])
const franchises = ref([])
const showGuDropdown = ref(false)
const showDongDropdown = ref(false)
const showCategoryDropdown = ref(false)
const showFranchiseDropdown = ref(false)
const currentStep = ref(1)
const location = ref('')
const category = ref('')
const franchise = ref('')
const storeSize = ref('')
const selectedFloor = ref('')
const gu = ref('')
const dong = ref('')


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
  gu.value = payload
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
  dong.value = dongItem
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

const nextStep = () => {
  currentStep.value++
}

const prevStep = () => {
  currentStep.value--
}

const showCostBreakdown = () => {
  let size = storeSize.value == 'small' ? 10 : 20;
  myFranchise.value.storeSize = size
  let fee = selectedFloor.value == 'other' ? false : true;
  myFranchise.value.floor = fee ? '1층' : '1층 외'
  getFranchisesFee(
    franchise.value.id,
    dong.value.code,
    size,
    fee,
    ({data}) => {
      console.log(data)
      myFranchise.value.id = data.id
      myFranchise.value.name = data.name;
      myFranchise.value.costs = [
          { name: '가맹비', amount: data.franchise_fee },
          { name: '보증금', amount: data.deposit },
          { name: '교육비', amount: data.education_fee },
          { name: '인테리어 비용', amount: data.interior },
          { name: '기타비용', amount: data.other_fee },
          { name:'임대료', amount:Math.floor(size * data.rent_fee/ 1000)}
      ]
      myFranchise.value.link = data.link
      myFranchise.value.likeId = data.like_id
      currentStep.value = 3
    },
    (error) => {
      console.log(error)
    }
  )
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

onMounted(() => {
  const modalElement = document.getElementById('exampleModal2');

  modalElement.addEventListener('hide.bs.modal', () => {
    currentStep.value = 1
    location.value = ''
    category.value = ''
    franchise.value = ''
    storeSize.value = ''
    selectedFloor.value = ''
    gu.value = ''
    dong.value = ''
  });

  getGuInfos(),
  getTypes()
})

const myFranchise = ref({
    id : '',
    gu : gu,
    dong : dong,
    name: '',
    storeSize : storeSize,
    floor : selectedFloor,
    cost: 0,
    costs: [
      { name: '임대료', amount: 0 },
      { name: '가맹비', amount: 0 },
      { name: '보증금', amount: 0 },
      { name: '교육비', amount: 0 },
      { name: '인테리어 비용', amount:  0},
      { name: '기타비용', amount: 0 },
    ],
    link: '',
    likeId: -1
  },
)
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
