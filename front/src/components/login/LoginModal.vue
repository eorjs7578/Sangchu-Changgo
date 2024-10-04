<template>
  <div v-if="isModalVisible">
    <!-- 모달 백그라운드 -->
    <div class="modal-backdrop fade show"></div>
    
    <!-- 로그인 모달 -->
    <div class="modal d-block" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content bg-light">
          <div class="modal-header bg-success text-white">
            <h5 class="modal-title">로그인이 필요합니다</h5>
            <!-- Close Button -->
            <button type="button" class="btn-close text-white" @click="closeModal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <p>로그인이 필요합니다. 로그인하시겠습니까?</p>
          </div>
          <div class="modal-footer">
            <!-- Login Button -->
            <button class="btn btn-success w-100 mb-2" @click="handleLoginClick">
              <i class="bi bi-box-arrow-in-right me-2"></i>로그인
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const isModalVisible = ref(true);
const router = useRouter();

const closeModal = () => {
  isModalVisible.value = false;
};

// 로그인 버튼 클릭 핸들러
const handleLoginClick = () => {
  closeModal(); // 모달 닫기
  router.push('/members/login'); // 로그인 페이지로 이동
};
</script>

<style scoped>
.modal-backdrop {
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  z-index: 1040;
}

.modal-content {
  border-radius: 8px;
}

.modal-header {
  padding: 1rem;
  border-bottom: none;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-footer {
  padding: 1rem;
  border-top: none;
}

.btn-success {
  background-color: #28a745;
}

.btn-success:hover {
  background-color: #218838;
}

.btn-close {
  background: none;
  border: none;
  font-size: 1.5rem;
  color: white;
  cursor: pointer;
}

.btn-close:hover {
  color: #f8f9fa;
}
</style>
