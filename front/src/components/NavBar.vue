<template>
  <div class="container">
    <!-- 첫 번째 사이드바 -->
    <nav class="navbar">
      <h5>상추창고</h5>
      <ul>
        <li @click="closeCommunitySubmenu"><router-link to="/marketanalysis">상권분석</router-link></li>
        <li @click="closeCommunitySubmenu"><router-link to="/interestareas">관심상권</router-link></li>
        <li @click="toggleCommunitySubmenu" class="link">
          커뮤니티
        </li>
         <!-- 프랜차이즈 메뉴 -->
         <li @click="toggleFranchiseSubmenu" class="link">
          프랜차이즈
        </li>
        
        <!-- 프랜차이즈 하위 메뉴 -->
        <ul v-show="isFranchiseOpen ">
          <li class="link" data-bs-toggle="modal" data-bs-target="#exampleModal">관심프차</li>
          <FavoriteFranchise class="modal fade fullscreen-modal" id="exampleModal"></FavoriteFranchise>
          <li class="link" data-bs-toggle="modal" data-bs-target="#exampleModal">예상비용</li>
          <FranchiseFee class="modal fade fullscreen-modal" id="exampleModal"></FranchiseFee>
        </ul>

      </ul>

      <!-- 로그인/회원가입 링크 -->
      <ul class="auth-links">
        <li @click="closeCommunitySubmenu"><router-link to="/login">로그인</router-link></li>
        <li @click="closeCommunitySubmenu"><router-link to="/signup">회원가입</router-link></li>
      </ul>
    </nav>

    <!-- 두 번째 서브메뉴 (커뮤니티를 클릭할 때 표시) -->
    <nav class="submenu" v-if="isCommunityOpen">
      <h1>커뮤니티</h1>
      <ul>
        <li><router-link to="/community/marketboard">상권 게시판</router-link></li>
        <li><router-link to="/community/franchiseboard">프랜차이즈 게시판</router-link></li>
        <li><router-link to="/community/propertyboard">상권 매물 게시판</router-link></li>
      </ul>
    </nav>

    <!-- 메인 콘텐츠 영역 -->
    <div class="content">

    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import FranchiseFee from '@/views/franchise/FranchiseFee.vue'
import FavoriteFranchise from '@/views/franchise/FavoriteFranchise.vue'


const isCommunityOpen = ref(false)
const isFranchiseOpen = ref(false)

const toggleCommunitySubmenu = () => {
  isCommunityOpen.value = !isCommunityOpen.value;
}

const closeCommunitySubmenu = () => {
  isCommunityOpen.value = false
}

const toggleFranchiseSubmenu = () => {
  isCommunityOpen.value = false
  isFranchiseOpen.value = !isFranchiseOpen.value
}
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
  width: 200px; /* 사이드바 너비 */
  background-color: #f8f8f8;
  padding: 2rem;
  height: 100vh;
}

.navbar ul {
  list-style-type: none;
  padding: 0;
}

.navbar ul li {
  margin-bottom: 1.5rem;
  font-size: 1.2rem;
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
  position: absolute;
  bottom: 2rem;
  left: 2rem;
}

.auth-links li {
  margin-bottom: 1.5rem;
  font-size: 1.2rem;
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
</style>
