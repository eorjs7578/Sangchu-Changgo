<template>
  <div class="modal fade fullscreen-modal" id="favoriteArea" tabindex="-1" aria-labelledby="favoriteAreaLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header bg-success text-white">
          <h5 class="modal-title" id="favoriteAreaLabel">관심 상권 리스트</h5>
          <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
        <div class="modal-body">
          <!-- 관심 상권이 없을 경우 메시지 표시 -->
          <p v-if="favoriteAreas.length === 0">관심 상권을 추가해보세요!</p>
          
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

const favoriteAreas = ref([]);

const fetchFavoriteAreas = async () => { 
  try {
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
  }
};

const toggleFavorite = async (area) => {
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

const router = useRouter();

const navigateToMarketAnalysis = (area) => {
  window.location.href = `http://localhost:5173/marketanalysis?areaId=${area.area_id}`
  // router.replace({ path : "/marketanalysis", query: { areaId: area.area_id } });
};

const handleModalShown = () => {
  fetchFavoriteAreas();
};

onMounted(() => {
  // Add event listener for modal shown event
  const modalElement = document.getElementById('favoriteArea');
  modalElement.addEventListener('shown.bs.modal', handleModalShown);
});

onUnmounted(() => {
  // Remove event listener when component is unmounted
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
