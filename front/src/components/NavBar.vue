<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light flex-column vh-100 p-3" style="width: 250px;">
    <router-link to="/marketanalysis" class="navbar-brand mb-4">
      <img src="@/assets/img/sangchuLogo2.png" alt="상추창고" height="112" width="150">
    </router-link>
    <ul class="nav flex-column mb-auto w-100">
      <li class="nav-item">
        <router-link to="/marketanalysis" class="nav-link" @click="closeCommunitySubmenu">
          <i class="bi bi-bar-chart-fill me-2"></i>상권분석
        </router-link>
      </li>
      <li class="nav-item">
        <router-link to="/interestareas" class="nav-link" @click="closeCommunitySubmenu">
          <i class="bi bi-star-fill me-2"></i>관심상권
        </router-link>
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
            <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#exampleModal1" @click="toggleFavorite">
              <i class="bi bi-heart-fill me-2"></i>관심 프랜차이즈
            </a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#" data-bs-toggle="modal" data-bs-target="#exampleModal2">
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
            <router-link to="/members/mypage" class="btn btn-link text-decoration-none w-100 text-start">마이페이지</router-link>
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
    <FavoriteFranchise :franchise="favoriteFranchises" class="modal fade fullscreen-modal" id="exampleModal1"></FavoriteFranchise>
    <FranchiseFee class="modal fade fullscreen-modal" id="exampleModal2"></FranchiseFee>
  </nav>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAccountStore } from '@/stores/useAccountStore';
import { getFavoriteFranchises } from '@/api/franchise';
import { api } from '@/lib/api';
import FranchiseFee from '@/views/franchise/FranchiseFee.vue';
import FavoriteFranchise from '@/views/franchise/FavoriteFranchise.vue';

const isCommunityOpen = ref(false);
const isFranchiseOpen = ref(true);
const isAvatarMenuOpen = ref(false);
const favoriteFranchises = ref([]);

const router = useRouter();
const store = useAccountStore();


const toggleFavorite = () => {
  getFavoriteFranchises(
    ({data}) => {
      console.log(data)
      favoriteFranchises.value = transformData(data)
    },
    (error) => {
      console.log(error)
    }
  )
}

const transformData = (data) => {
  return data.map((item) => ({
    id: item.franchise_fee_dto.id,
    gu: item.gu,  // 실제 구 데이터로 채워야 함
    dong: item.dong,  // 실제 동 데이터로 채워야 함
    name: item.franchise_fee_dto.name,
    storeSize: item.size == 'small' ? 10 : 20,  // storeSize 값을 적절히 입력해야 함
    floor: item.floor == 'other' ? '1층' : '1층 외',  // floor 값을 적절히 입력해야 함
    costs: [
      { name: '임대료', amount: Math.floor(item.franchise_fee_dto.rent_fee * item.size / 1000) },
      { name: '가맹비', amount: item.franchise_fee_dto.franchise_fee },
      { name: '보증금', amount: item.franchise_fee_dto.deposit },
      { name: '교육비', amount: item.franchise_fee_dto.education_fee },
      { name: '인테리어 비용', amount: item.franchise_fee_dto.interior * item.size },
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
function logout() {
  api.post("/api/members/logout").then(() => {
    store.isAuthenticated = false;
    store.userInfo = {};
    isAvatarMenuOpen.value = false; // 드롭다운 메뉴 닫기
    router.push("/").then(() => {
      window.location.reload();
    })
})}


onMounted(() => {
  isCommunityOpen.value = false;
  isFranchiseOpen.value = true;
});
</script>

<style scoped>
.navbar {
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}
.nav-link {
  color: #333;
}
.nav-link:hover {
  background-color: #e9ecef;
}
</style>
