<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light flex-column vh-100 px-3 py-1" style="width: 20vw;">
    <!-- <router-link to="/marketanalysis" class="navbar-brand mb-4">
      <img src="@/assets/img/sangchuLogo2.png" alt="상추창고" height="112" width="150">
    </router-link> -->
    <a href="/marketanalysis" class="navbar-brand mb-1">
      <img src="@/assets/img/sangchuLogo2.png" alt="상추창고" height="112" width="150">
    </a>
    <div class="w-100 mt-1 mb-4">
        <div class="search-input-container" id="area-search">
          <input
            ref="areaSearchQ"
            type="text"
            class="custom-input"
            placeholder="상권 검색"
            @input="handleAreaInput"
            @keyup.enter="handleAreaEnter"
          />
          <button class="search-button" @click="searchArea">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
              <circle cx="11" cy="11" r="8"></circle>
              <line x1="21" y1="21" x2="16.65" y2="16.65"></line>
            </svg>
          </button>
          <!-- 추천 검색어 목록 -->
          <div v-if="areaRecommendations.length > 0" class="recommendations-container">
            <ul class="list-group">
              <li 
                v-for="recommendation in areaRecommendations" 
                :key="recommendation" 
                class="list-group-item list-group-item-action" 
                @click="selectAreaRecommendation(recommendation)"
                @mousedown.prevent
              >
                {{ recommendation }}
              </li>
            </ul>
          </div>
        </div>
      </div>
    <ul class="nav flex-column mb-2 w-100">
      <li class="nav-item">
        <a href="/marketanalysis" class="nav-link">
          <i class="bi bi-bar-chart-fill me-2"></i>상권분석
        </a>
      </li>
      <li class="nav-item">
        <a href="#" class="nav-link" @click.prevent="toggleFavoriteArea">
          <i class="bi bi-star-fill me-2"></i>관심상권
        </a>
      </li>
      <li class="nav-item">
        <router-link to="/community" class="nav-link" @click="toggleCommunitySubmenu">
          <i class="bi bi-people-fill me-2"></i>커뮤니티
        </router-link>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#" @click.prevent="toggleFranchiseSubmenu">
          <i class="bi bi-shop me-2"></i>프랜차이즈
          <i :class="['bi', isFranchiseOpen ? 'bi-chevron-up' : 'bi-chevron-down', 'ms-2']"></i>
        </a>
        <ul v-if="isFranchiseOpen" class="nav flex-column ms-3 mt-2">
          <li class="nav-item">
            <a class="nav-link" href="#" @click="toggleFavorite" compact-submenu>
              <i class="bi bi-heart-fill me-2"></i>관심 프랜차이즈
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" @click.prevent="toggleEstimatedCost">
              <i class="bi bi-currency-dollar me-2"></i>예상비용
            </a>
          </li>
        </ul>
      </li>
    </ul>
    <div class="mt-auto w-100">
      
      <template v-if="store.isAuthenticated">
        <button @click="toggleAvatarMenu" class="btn btn-outline-success w-100 mb-2">
          <i class="bi bi-person-fill me-2"></i>내 계정
        </button>
        <div v-if="isAvatarMenuOpen" class="card mt-2">
          <div class="card-body p-0">
            <router-link to="/members/mypage" class="btn btn-link text-decoration-none w-100 text-start">회원정보수정</router-link>
            <button @click="logout" class="btn btn-link text-decoration-none w-100 text-start text-danger">로그아웃</button>
          </div>
        </div>
      </template>
      <template v-else>
        <router-link to="/members/login" class="btn btn-success w-100 mb-2">
          <i class="bi bi-box-arrow-in-right me-2"></i>로그인
        </router-link>
        <router-link to="/members/signup" class="btn btn-outline-success w-100">
          <i class="bi bi-person-plus-fill me-2"></i>회원가입
        </router-link>
      </template>
    </div>
    <FavoriteFranchise v-show="isAuthenticated" :franchise="favoriteFranchises" class="modal fade fullscreen-modal" id="exampleModal1"></FavoriteFranchise>
    <FranchiseFee class="modal fade fullscreen-modal" id="exampleModal2"></FranchiseFee>
    <FavoriteArea class="modal fade fullscreen-modal" id="favoriteArea"></FavoriteArea>
  </nav>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/stores/useAccountStore';
import { getFavoriteFranchises } from '@/api/franchise';
import { api } from '@/lib/api';
import FranchiseFee from '@/views/franchise/FranchiseFee.vue';
import FavoriteFranchise from '@/views/franchise/FavoriteFranchise.vue';
import {Modal} from 'bootstrap'
import FavoriteArea from '@/views/InterestAreas.vue';
const { VITE_VUE_FRONT_URL} = import.meta.env;

const isCommunityOpen = ref(false);
const isFranchiseOpen = ref(false);
const isAvatarMenuOpen = ref(false);
const favoriteFranchises = ref([]);

const router = useRouter();
const store = useAccountStore();

const areaSearchQ = ref(null);
const areaRecommendations = ref([]);

const isAuthenticated = ref(store.isAuthenticated); // store의 인증 상태 확인
const handleFavoriteClick = () => {
  
}

const navigateToAreaAnalysis = (area) => {
  response.get(`/api/area-info`, {areaName})
  window.location.href = VITE_VUE_FRONT_URL+`marketanalysis?areaId=${area.area_id}`
};

function handleAreaInput() {
  debouncedGetAreaRecommendations();
}

async function handleAreaEnter() {
  if (areaRecommendations.value) {
  const response = await api.get(`api/area-info/name/${areaRecommendations.value[0]}`);
  const areaId = response.data;
  window.location.href = VITE_VUE_FRONT_URL + `marketanalysis?areaId=${areaId}`;
  areaRecommendations.value = [];
  } else {
    alert("검색어를 입력해 주세요.")
  }
}

const debouncedGetAreaRecommendations = debounce(async () => {
  console.log("debo: " + areaSearchQ.value.value + " "  + areaSearchQ.value)
  if (areaSearchQ.value && areaSearchQ.value.value.length > 0) {
    try {
      const response = await api.get(`/api/recommendation/area?areaName=${areaSearchQ.value.value}`);
      areaRecommendations.value = response.data.result; 
    } catch (error) {
      console.error("Error fetching area recommendations:", error);
    }
  } else {
    areaRecommendations.value = [];
  }
}, 300);

const selectAreaRecommendation = (recommendation) => {
  areaSearchQ.value.value = recommendation;
  areaRecommendations.value = [];
  searchArea();
}

async function searchArea() {
  if (areaSearchQ.value && areaSearchQ.value.value.length > 0) {
    console.log("Searching for area:", areaSearchQ.value.value);
    try {
      const response = await api.get(`api/area-info/name/${areaSearchQ.value.value}`);
      const areaId = response.data;
      console.log(response);
      if (!response.data.length) {
        alert('그러한 상권이 없습니다.')
      }
      else{
       window.location.href = VITE_VUE_FRONT_URL + `marketanalysis?areaId=${areaId}`;
      }
    } catch (error) {
      console.error("Error fetching area info:", error);
    }
  } else {
    console.warn("Area search query is empty or undefined.");
  }
}


watch([areaSearchQ], () => {
  console.log(areaSearchQ.value)
  if (areaSearchQ.value.value) {
    areaRecommendations.value = [];
  }
  debouncedGetAreaRecommendations();
});

function debounce(func, wait) {
  let timeout;
  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout);
      func(...args);
    };
    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
}

const toggleFavoriteArea = () => {
  if (store.isAuthenticated) {
    console.log("로그인 완료")
    // 여기에 관심상권 관련 로직을 추가합니다.
    // 예: API 호출, 데이터 처리 등
    let favoriteAreaModal = new Modal(document.getElementById('favoriteArea'));
    favoriteAreaModal.show();
  } else {
    console.log("로그인 필요")
    window.alert("로그인이 필요합니다!")
    router.push({name : 'login'})
  }
}

const toggleFavorite = () => {
  if(store.isAuthenticated) {
    console.log("로그인 완")
    getFavoriteFranchises(
    ({data}) => {
      console.log(data)
      favoriteFranchises.value = transformData(data)
      let myModal = new Modal(document.getElementById('exampleModal1'));
      myModal.show();
    },
    (error) => {
      console.log(error)
    }
  )
  } else {
    console.log("로그인 필요")
    window.alert("로그인이 필요합니다!")
    router.push({name : 'login'})
  }
  
}

const toggleEstimatedCost = () => {
  if (store.isAuthenticated) {
    console.log("로그인 완료")
    // 여기에 예상비용 관련 로직을 추가합니다.
    // 예: API 호출, 데이터 처리 등
    let estimatedCostModal = new Modal(document.getElementById('exampleModal2'));
    estimatedCostModal.show();
  } else {
    console.log("로그인 필요")
    window.alert("로그인이 필요합니다!")

    router.push({name : 'login'})
  }
}



const transformData = (data) => {
  return data.map((item) => ({
    id: item.franchise_fee_dto.id,
    gu: item.gu,  // 실제 구 데이터로 채워야 함
    dong: item.dong,  // 실제 동 데이터로 채워야 함
    name: item.franchise_fee_dto.name,
    storeSize: item.size,  // storeSize 값을 적절히 입력해야 함
    floor: item.floor == false ? '1층 외' : '1층',  // floor 값을 적절히 입력해야 함
    costs: [
      { name: '가맹비', amount: item.franchise_fee_dto.franchise_fee },
      { name: '보증금', amount: item.franchise_fee_dto.deposit },
      { name: '교육비', amount: item.franchise_fee_dto.education_fee },
      { name: '인테리어 비용', amount: item.franchise_fee_dto.interior * item.size },
      { name: '임대료', amount: Math.floor(item.franchise_fee_dto.rent_fee * item.size / 1000) },
      { name: '기타비용', amount: item.franchise_fee_dto.other_fee }
    ],
    link: item.franchise_fee_dto.link,
    likeId: item.franchise_fee_dto.like_id
  }));
};

const toggleCommunitySubmenu = () => {
  isCommunityOpen.value = !isCommunityOpen.value;
};

const closeCommunitySubmenu = () => {
  isCommunityOpen.value = false;
};

const toggleFranchiseSubmenu = () => {
  isCommunityOpen.value = false;
  isFranchiseOpen.value = !isFranchiseOpen.value;
};

// 아바타 메뉴 토글 함수
const toggleAvatarMenu = () => {
  isAvatarMenuOpen.value = !isAvatarMenuOpen.value;
};

// 로그아웃 함수
async function logout() {
  await api.post("/api/members/logout")
    store.isAuthenticated = false;
    store.userInfo = {};
    isAvatarMenuOpen.value = false; // 드롭다운 메뉴 닫기
    router.push("/").then(() => {
      window.location.reload();
    })
}


onMounted(() => {
  isCommunityOpen.value = false;
});
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap');

.navbar {
  font-family: 'Noto Sans KR', sans-serif;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-right: 1px solid rgba(0, 0, 0, 0.1); /* Border for separation */
  background-color: #f8f9fa; /* Light background to distinguish from content */
}

.nav-link {
  color: #333;
  font-size: 1.1rem;
  font-weight: 500;
  transition: background-color 0.3s ease, color 0.3s ease;
  padding: 10px 15px;
}

.nav-link:hover {
  background-color: #e9ecef;
  color: #007bff;
  border-radius: 5px;
}

.navbar-brand img {
  max-width: 100%;
  height: auto;
}

.navbar .btn {
  font-weight: 600;
  transition: all 0.3s ease;
}

.navbar .btn-success {
  background-color: #28a745;
  border-color: #28a745;
}

.navbar .btn-outline-success:hover {
  background-color: #28a745;
  border-color: #28a745;
  color: #fff;
}

.card {
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

.btn-link {
  padding: 10px;
}
.btn-toggle-mode {
  background-color: transparent;
  border: none;
  font-size: 1rem;
  cursor: pointer;
  color: var(--text-color);
}

.search-input-container {
  position: relative;
  display: flex;
  align-items: center;
  background-color: #fff;
  border-radius: 20px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  overflow: visible;
}

.custom-input {
  flex-grow: 1;
  border: none;
  padding: 10px 15px;
  font-size: 14px;
  outline: none;
  background: transparent;
}

.search-button {
  position: absolute;
  right: 0;
  background-color: transparent;
  border: none;
  padding: 10px;
  cursor: pointer;
}

.search-button:hover {
  background-color: rgba(0, 0, 0, 0.05);
}

.recommendations-container {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  max-height: 200px;
  overflow-y: auto;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 0 0 20px 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.list-group-item {
  cursor: pointer;
  padding: 10px 15px;
  border: none;
  border-bottom: 1px solid #f0f0f0;
}

.list-group-item:last-child {
  border-bottom: none;
}

.list-group-item:hover {
  background-color: #f8f9fa;
}

.compact-menu .nav-link {
  padding: 8px 10px;
  font-size: 0.95rem;
}

.compact-submenu .nav-link {
  padding: 6px 10px;
  font-size: 0.9rem;
}
</style>
