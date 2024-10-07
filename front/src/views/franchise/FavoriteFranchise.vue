<template>
  <div class="modal fade" id="koreanModal" tabindex="-1" aria-labelledby="koreanModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered custom-modal-width custom-modal-height">
      <div class="modal-content">
        <div class="modal-header bg-success text-white">
          <h5 class="modal-title" id="koreanModalLabel">내 관심 프차</h5>
          <button @click="setParamsDefault" type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div v-if="franchises.length > 0" class="modal-body">
          <transition name="fade" mode="out-in">
            <!-- Step 1 -->
            <div v-if="currentStep === 1 || currentStep===2" key="step1" class="h-100 d-flex flex-column justify-content-between ">
                <div class="mb-4 overflow-auto custom-scroll"> <!-- 간격 조정 -->
                  <div class="row"> <!-- 간격 조정 -->
                    <div class="col-md-6" v-for="(franchise, index) in franchises" :key="index">
                      <div class="card mb-4" :class="{ 'border-success': isSelected(franchise) }" @click="selectFranchise(franchise)">
                        <FranchiseInfoCard :franchise="franchise" />
                      </div>
                    </div>
                  </div>
                </div>
                  <button @click="compareFranchises" class="btn btn-success w-100">비교하기 ({{selectedFranchises.length}}/2)</button>
            </div>

            <!-- Step 3 -->
            <div v-else-if="currentStep === 3" key="step3" class="h-100 d-flex flex-column justify-content-between">
              <div class="row">
                <div class="col-md-6" v-for="(franchise, index) in selectedFranchises" :key="index">
                    <div class="card mb-4">
                      <FranchiseInfoCard :franchise="franchise" />
                    </div>
                </div>
                </div>
                <button @click="prevStep" class="btn btn-success w-100">이전으로</button>
              </div>
          </transition>
        </div>
        <div v-if="franchises.length <= 0" class="modal-body d-flex justify-content-center align-items-center">
          <h3>관심 프랜차이즈를 등록해주세요!</h3>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineProps, watch } from 'vue'
import { getFavoriteFranchises, deleteFavoriteFranchise, addFavoriteFranchise} from '@/api/franchise.js'
import FranchiseInfoCard from '@/components/franchise/FranchiseInfoCard.vue';

const props = defineProps(['franchise'])
const currentStep = ref(1)
const selectedFranchises = ref([])
const franchises = ref([])

watch(
  () => props.franchise,  // 감시할 데이터
  (newFranchise) => {
    franchises.value = newFranchise;  // 데이터가 업데이트되면 반영
    console.log('Updated franchise list:', franchises.value);
  },
  { immediate: true }  // 컴포넌트가 로드되자마자 감시 시작
);

const selectFranchise = (franchise) => {
  const index = selectedFranchises.value.findIndex(f => f === franchise)
  if (index === -1 && selectedFranchises.value.length < 2) {
    selectedFranchises.value.push(franchise)
  } else if (index !== -1) {
    selectedFranchises.value.splice(index, 1)
  }
}

const isSelected = (franchise) => {
  return selectedFranchises.value.includes(franchise)
}

const compareFranchises = () => {
  console.log(franchises)
  currentStep.value = 3
}

const nextStep = () => {
  currentStep.value++
}

const prevStep = () => {
  currentStep.value--
}

const showCostBreakdown = () => {

  currentStep.value = 3
}


const setParamsDefault = () => {
  currentStep.value = 1
  selectedFranchises.value = []
}



onMounted(() => {
  const modalElement = document.getElementById('exampleModal1');
  
  modalElement.addEventListener('hide.bs.modal', () => {
    selectedFranchises.value = [];  // 모달이 닫힐 때 selectedFranchises 배열을 초기화
  });
})

</script>

<style scoped>
.custom-scroll {
  overflow-y: auto; /* 세로 스크롤 */
}

/* 스크롤바를 감추는 CSS */
.custom-scroll::-webkit-scrollbar {
  display: none;
}

.custom-scroll {
  -ms-overflow-style: none;  /* IE, Edge */
  scrollbar-width: none;  /* Firefox */
}

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
