<template>
  <div class="modal d-block" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content bg-light">
        <div class="modal-header bg-success text-white">
          <h5 class="modal-title">로그인이 필요합니다</h5>
        </div>
        <div class="modal-body">
          <p>로그인이 필요합니다. 로그인하시겠습니까?</p>
        </div>
        <div class="modal-footer">
          <!-- Login Button -->
          <router-link to="/members/login" class="btn btn-success w-100 mb-2">
            <i class="bi bi-box-arrow-in-right me-2"></i>로그인
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
// No specific logic required for this simple modal
</script>

<style scoped>
.modal {
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  border-radius: 8px;
}

.modal-header {
  padding: 1rem;
  border-bottom: none;
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
</style>
