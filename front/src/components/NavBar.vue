<template>
  <div class="contatiner">
    <!-- 첫 번째 사이드바 -->
    <nav class="navbar">
      <router-link to="/marketanalysis">
        <img src="@/assets/img/sangchuLogo2.png" alt="Sangchu Logo" class="logo" />
      </router-link>

      <ul class="nav-links">
        <li @click="closeCommunitySubmenu">
          <router-link to="/marketanalysis">상권분석</router-link>
        </li>
        <li @click="closeCommunitySubmenu">
          <router-link to="/interestareas">관심상권</router-link>
        </li>
        <li @click="toggleCommunitySubmenu" >
          <router-link to="/community">커뮤니티</router-link>
        </li>

        <!-- 프랜차이즈 메뉴 -->
        <li @click="toggleFranchiseSubmenu" class="link">프랜차이즈</li>

        <!-- 프랜차이즈 하위 메뉴 -->
        <ul v-show="isFranchiseOpen ">
          <li class="link" data-bs-toggle="modal" data-bs-target="#exampleModal1" @click="toggleFavorite">관심프차</li>
          <FavoriteFranchise :franchise="favoriteFranchises" class="modal fade fullscreen-modal" id="exampleModal1"></FavoriteFranchise>
          <li class="link" data-bs-toggle="modal" data-bs-target="#exampleModal2">예상비용</li>
          <FranchiseFee class="modal fade fullscreen-modal" id="exampleModal2"></FranchiseFee>
        </ul>
      </ul>

      <!-- 로그인/회원가입 또는 아바타 이미지 -->
      <div class="bottom-container">
        <ul class="auth-links">
          <li
              v-if="!store.isAuthenticated"
              @click="closeCommunitySubmenu">
            <router-link to="/members/login">로그인</router-link>
          </li>
          <li
              v-if="!store.isAuthenticated"
              @click="closeCommunitySubmenu">
            <router-link to="/members/signup">회원가입</router-link>
          </li>
          <li v-else class="avatar-container">
            <img src="@/assets/img/account.png" alt="User Avatar" class="avatar-image" @click="toggleAvatarMenu" />
            <div v-show="isAvatarMenuOpen" class="avatar-dropdown">
              <router-link to="/members/mypage">마이페이지</router-link>
              <button @click="logout" class="logout-btn">로그아웃</button>
            </div>
          </li>
        </ul>
      </div>
    </nav>

    <!-- 두 번째 서브메뉴 (커뮤니티를 클릭할 때 표시) -->
    <nav class="submenu" v-if="isCommunityOpen">
      <h1>커뮤니티</h1>
      <ul>
        <li><router-link to="/CreatePost">상권 게시판</router-link></li>
        <li><router-link to="/PostList">프랜차이즈 게시판</router-link></li>
        <li><router-link to="/community/propertyboard">상권 매물 게시판</router-link></li>
      </ul>
    </nav>

    <!-- 메인 콘텐츠 영역 -->
    <div class="content">

    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import FranchiseFee from '@/views/franchise/FranchiseFee.vue';
import FavoriteFranchise from '@/views/franchise/FavoriteFranchise.vue';
import { getFavoriteFranchises} from '@/api/franchise.js'
import {useAccountStore} from "@/stores/useAccountStore.js";
import {api} from "@/lib/api.js";
import {useRouter} from "vue-router";

const isCommunityOpen = ref(false)
const isFranchiseOpen = ref(true)
const favoriteFranchises = ref([])

const isLoggedIn = ref(false)
const isAvatarMenuOpen = ref(false)

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


// 페이지가 로드될 때 로그인 상태 확인 (onMounted 사용)
onMounted(() => {
  isLoggedIn.value = store.isAuthenticated;
  isCommunityOpen.value = false
  isFranchiseOpen.value = !isFranchiseOpen.value
});

</script>

<style scoped>
/* 컨테이너 설정 */
.container {
  display: flex;
  height: 100vh;
  width: 100vw;
}

/* 네비게이션 바 설정 */
.navbar {
  position: relative;
  width: 12vw;
  background-color: #f8f8f8;
  padding: 1rem;
  height: 100vh; /* 네비게이션 바의 높이를 화면 전체로 설정 */
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* 요소들을 위아래로 균일하게 배치 */
}

/* 네비게이션 아이템 중앙 정렬 */
.nav-links {
  flex-grow: 1;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 2rem; /* 항목 사이 간격 추가 */
}

.navbar ul {
  list-style-type: none;
  padding: 0;
}

.navbar ul li {
  margin-bottom: 1.5rem;
  font-size: 1.2rem;
  text-align: center;
}

/* 커뮤니티 링크에 대한 hover 스타일 추가 */
.link:hover {
  color: blue; /* 마우스를 올렸을 때 파란색으로 변경 */
  cursor: pointer; /* 커서를 포인터로 변경 */
}

/* 두 번째 서브메뉴 */
.submenu {
  width: 300px;
  background-color: #e6f0ff;
  padding: 2rem;
  height: 100vh;
  transition: transform 0.3s ease-in-out;
}

/* 메인 콘텐츠 영역 */
.content {
  flex-grow: 1; /* 메인 콘텐츠가 사이드바를 제외한 나머지 공간을 채움 */
  padding: 2rem;
  background-color: #f3f4f6;
  overflow-y: auto;
}

/* 로그인/회원가입 링크를 네비게이션 바의 하단에 고정 */
.auth-links {
  /* position: absolute; */
  bottom: 0;
  left: 2rem;
  margin: 0;
}

/* 아바타 컨테이너를 하단으로 고정 */
.bottom-container {
  margin-top: auto;
  display: flex;
  justify-content: center;
}

/* 아바타 이미지 스타일 */
.avatar-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
}

.avatar-image {
  width: 50px;
  height: 50px;
  border-radius: 50%;
  margin-bottom: 0.5rem;
  cursor: pointer;
}

/* 아바타 드롭다운을 오른쪽으로 배치 */
.avatar-dropdown {
  position: absolute;
  left: 80px; /* 아바타 오른쪽으로 드롭다운 메뉴 배치 */
  top: -100px;
  background-color: #f3e8fc;
  border: 1px solid #ccc;
  border-radius: 8px;
  width: 150px;
  padding: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 100;
}

.avatar-dropdown a,
.avatar-dropdown button {
  display: block;
  padding: 8px 12px;
  color: #333;
  text-align: center;
  text-decoration: none;
  cursor: pointer;
}

.avatar-dropdown button {
  background: none;
  border: none;
  color: #dc3545;
}

.avatar-dropdown button:hover {
  background-color: #f7d7d7;
}

.avatar-dropdown a:hover {
  background-color: #eee;
}

/* 기본 링크 스타일 */
.auth-links a,
.navbar ul li a {
  text-decoration: none;
  color: inherit;
}

/* 마우스를 올렸을 때 파란색으로 변경 */
.auth-links a:hover,
.navbar ul li a:hover,
.submenu ul li a:hover {
  color: blue;
}

/* 로고 이미지 스타일 */.logo {
  width: 150px;
  margin-bottom: 2rem;
  cursor: pointer;
}

</style>
