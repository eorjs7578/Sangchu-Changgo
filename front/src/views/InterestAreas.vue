<template>
  <div v-if="!showLoginPopup" class="modal d-block" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
      <div class="modal-content bg-light">
        <div class="modal-header bg-success text-white">
          <h5 class="modal-title">관심상권 리스트</h5>
          <router-link to="/marketanalysis" class="btn-close btn-close-white" aria-label="Close"></router-link>
        </div>
        <div class="modal-body">
          <ul class="list-group">
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
          <button class="btn btn-secondary" @click="goBack">닫기</button>
        </div>
      </div>
    </div>
  </div>

  <!-- Import and use the LoginModal component here -->
  <LoginModal v-if="showLoginPopup" />
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { api } from "@/lib/api.js"
import LoginModal from "@/components/login/LoginModal.vue";  // Import the LoginModal component

const favoriteAreas = ref([]);
const showLoginPopup = ref(false);
const maxRetries = 2; // Maximum number of retries
const retryDelay = 2000; // Delay between retries (in milliseconds)

const fetchFavoriteAreas = async (retryCount = 0) => {
  try {
    console.log(`Attempt ${retryCount + 1}: Requesting favorite areas...`);

    // Create a timeout promise that rejects after 10 seconds
    const timeout = new Promise((_, reject) =>
      setTimeout(() => reject(new Error('Request timed out')), 10000)
    );

    // Make the request and race it against the timeout
    const response = await Promise.race([
      api.get('/api/favorite/areas/list'), // Use api.get instead of axios.get
      timeout
    ]);

    // Populate the favoriteAreas data, and add `isFavorite` flag to each area
    favoriteAreas.value = response.data.areaList.map(area => ({
      ...area,
      isFavorite: true // Mark it as a favorite (since it came from the favorite list)
    }));

    console.log('Request succeeded');
  } catch (error) {
    console.error('Error:', error.message);

    if (error.response && error.response.status === 401) {
      showLoginPopup.value = true;
    } else if (retryCount < maxRetries) {
      console.log(`Retrying in ${retryDelay / 1000} seconds...`);
      setTimeout(() => fetchFavoriteAreas(retryCount + 1), retryDelay);
    } else {
      console.error('Failed to fetch favorite areas after multiple attempts');
    }
  }
};

const toggleFavorite = async (area) => {
  if (area.isFavorite) {
    // Send a DELETE request to remove the favorite
    try {
      await api.delete(`/api/favorite/areas/${area.areaId}`);
      area.isFavorite = false; // Mark as not favorite in the UI
    } catch (error) {
      console.error('Failed to remove favorite area:', error);
    }
  } else {
    // Send a POST request to add the favorite
    try {
      await api.post('/api/favorite/areas', { areaId: area.areaId });
      area.isFavorite = true; // Mark as favorite in the UI
    } catch (error) {
      console.error('Failed to add favorite area:', error);
    }
  }
};

const router = useRouter();

const navigateToMarketAnalysis = (area) => {
  console.log(`Navigating to market analysis for area: ${area.name}`);
  router.push("/marketanalysis");
};

const goBack = () => {
  router.back();
};

onMounted(() => {
  fetchFavoriteAreas();
});
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

.btn-close {
  background-color: transparent;
  border: none;
  font-size: 1.25rem;
  padding: 0.75rem;
}

.btn-success {
  background-color: #28a745;
}

.btn-success:hover {
  background-color: #218838;
}

.btn-secondary {
  background-color: #6c757d;
}

.btn-secondary:hover {
  background-color: #5a6268;
}

.badge {
  cursor: pointer;
}

.area-name {
  cursor: pointer;
  color: blue; /* Optionally style the clickable area name */
}
.area-name:hover {
  text-decoration: underline;
}
</style>
