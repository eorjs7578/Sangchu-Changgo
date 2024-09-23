<template>
    <!-- 첫 번째 사이드바 -->
    <nav class="navbar">
      <h1>상추창고</h1>
      <ul>
        <li @click="closeCommunitySubmenu"><router-link to="/marketanalysis">상권분석</router-link></li>
        <li @click="closeCommunitySubmenu"><router-link to="/interestareas">관심상권</router-link></li>
        <li @click="toggleCommunitySubmenu" class="community-link">
          커뮤니티
        </li>
        <li @click="closeCommunitySubmenu"><router-link to="/franchise">프랜차이즈</router-link></li>
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
        <li><router-link to="/community/marketBoard">상권 게시판</router-link></li>
        <li><router-link to="/community/franchiseboard">프랜차이즈 게시판</router-link></li>
        <li><router-link to="/community/propertyboard">상권 매물 게시판</router-link></li>
      </ul>
    </nav>

    <!-- 메인 콘텐츠 영역
    <div class="content">
    </div> -->
</template>


<script setup>
import {ref} from 'vue'

const isCommunityOpen = ref(false)

const toggleCommunitySubmenu = () => {
  isCommunityOpen.value = !isCommunityOpen.value;
}

const closeCommunitySubmenu = () => {
  isCommunityOpen.value = false
}
</script>

<style scoped>
.container {
  display: flex;
}

.navbar {
  position: relative;
  width: 150px;
  background-color: #f8f8f8;
  padding: 2rem;
  height: 100vh; /* 네비게이션 바의 높이를 화면 전체로 설정 */
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
.community-link:hover {
  color: blue; /* 마우스를 올렸을 때 파란색으로 변경 */
  cursor: pointer; /* 커서를 포인터로 변경 */
}

/* 두 번째 서브메뉴 */
.submenu {
  width: 300px;
  background-color: #e6f0ff; /* 하늘색 배경 */
  padding: 2rem;
  height: 100vh;
  text-align: center;
}

.submenu ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.submenu ul li{
  margin-bottom: 1.5rem;
  font-size: 1.2rem;
}

.submenu ul li a {
  text-decoration: none;
  color: inherit; /* 기본 상태에서는 부모 요소의 색상 유지 */
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
.auth-links a, .navbar ul li a {
  text-decoration: none;
  color: inherit; /* 기본 상태에서는 부모 요소의 색상 유지 */
}

/* 현재 페이지에 해당하는 링크를 파란색으로 유지 */
.active-link {
  color: blue;
}

/* 마우스를 올렸을 때 파란색으로 변경 */
.auth-links a:hover, .navbar ul li a:hover, .submenu ul li a:hover {
  color: blue;
}
</style>
