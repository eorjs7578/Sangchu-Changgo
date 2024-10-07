<template>
  <div class="modal fade fullscreen-modal" id="favoriteArea" tabindex="-1" aria-labelledby="favoriteAreaLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header bg-success text-white">
          <h5 class="modal-title" id="favoriteAreaLabel">관심 상권 리스트</h5>
          <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <!-- 로딩 중일 때 표시할 내용 -->
          <p v-if="isLoading">로딩 중...</p>
          
          <!-- 로딩이 완료되고 관심 상권이 없을 경우 메시지 표시 -->
          <p v-else-if="!isLoading && favoriteAreas.length === 0">관심 상권을 추가해보세요!</p>
          
          <!-- 관심 상권이 있을 경우 리스트 출력 -->
          <ul v-else class="list-group">
            <li v-for="area in favoriteAreas" :key="area.areaId" class="list-group-item d-flex justify-content-between align-items-center">
              <!-- When the area name is clicked, navigate to the marketanalysis page -->
              <span class="area-name" @click="navigateToMarketAnalysis(area)">
                {{ area.name }}
              </span>
              <span
                :class="{'badge bg-success rounded-pill': area.isFavorite, 'badge bg-secondary rounded-pill': !area.isFavorite}"
                @click="toggleFavorite(area)">
                {{ area.isFavorite ? "♥" : "♡" }}
              </span>
            </li>
          </ul>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" data-bs-dismiss="modal">닫기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import { api } from "@/lib/api.js"
import { useAccountStore } from "@/stores/useAccountStore";
const { VITE_VUE_FRONT_URL} = import.meta.env;
const accountStore = useAccountStore();
const router = useRouter();
const favoriteAreas = ref([]);
const isLoading = ref(true);

const fetchFavoriteAreas = async () => { 
  if (!accountStore.isAuthenticated) {
    isLoading.value = false;
    return;
  }

  try {
    isLoading.value = true;
    const response = await api.get(`/api/favorite/areas/list`);
    if (response.data.area_list) {
      favoriteAreas.value = response.data.area_list.map(area => ({
        ...area,
        isFavorite: true 
      }));
      console.log(favoriteAreas.value)
    } else {
      favoriteAreas.value = [];
    }
  } catch (error) {
    console.error('Failed to fetch favorite areas:', error);
  } finally {
    isLoading.value = false;
  }
};

const toggleFavorite = async (area) => {
  if (!accountStore.isAuthenticated) {
    router.push('/members/login');
    return;
  }

  try {
    if (area.isFavorite) {
      await api.delete(`/api/favorite/areas/${area.favorite_id}`);
      area.isFavorite = false;
    } else {
      await api.post('/api/favorite/areas', { area_id: area.area_id });
      window.location.reload();
    }
  } catch (error) {
    console.error('Failed to toggle favorite:', error);
  }
};

const navigateToMarketAnalysis = (area) => {
  window.location.href = VITE_VUE_FRONT_URL+`marketanalysis?areaId=${area.area_id}`
};

const closeModal = () => {
  const modalElement = document.getElementById('favoriteArea');
  modalElement.classList.remove('show');
  modalElement.style.display = 'none';
  document.body.classList.remove('modal-open');
  const modalBackdrop = document.querySelector('.modal-backdrop');
  if (modalBackdrop) {
    modalBackdrop.remove();
  }
};

const handleModalShown = () => {
  if (accountStore.isAuthenticated) {
    fetchFavoriteAreas();
  } else {
    closeModal();
    router.push('/members/login');
  }
};

onMounted(() => {
  const modalElement = document.getElementById('favoriteArea');
  modalElement.addEventListener('shown.bs.modal', handleModalShown);
});

onUnmounted(() => {
  const modalElement = document.getElementById('favoriteArea');
  modalElement.removeEventListener('shown.bs.modal', handleModalShown);
});
</script>

<style scoped>
.modal-content {
  border-radius: 8px;
}

.modal-header {
  padding: 1rem;
}

.modal-footer {
  padding: 1rem;
}

.area-name {
  cursor: pointer;
  color: blue;
}

.area-name:hover {
  text-decoration: underline;
}

.badge {
  cursor: pointer;
}

.btn-secondary {
  background-color: #6c757d;
}

.btn-secondary:hover {
  background-color: #5a6268;
}
</style>