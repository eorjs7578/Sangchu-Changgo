<template>
  <nav class="navbar">
    <h1>상추창고</h1>
    <br>
    <ul>
      <li><router-link to="/marketanalysis">상권분석</router-link></li>
      <li><router-link to="/interestareas">관심상권</router-link></li>
      <li><router-link to="/community">커뮤니티</router-link></li>
      <li><router-link to="/franchise">프렌차이즈</router-link></li>
    </ul>
    <!-- 로그인/회원가입 링크 -->
    <ul class="auth-links">
      <li><router-link to="/login">로그인</router-link></li>
      <li><router-link to="/signup">회원가입</router-link></li>
    </ul>
  </nav>
</template>

<script>
export default {
  name: 'NavBar'
}
</script>

<style scoped>
.navbar {
  position: relative;
  width: 15%;
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
.auth-links a:hover, .navbar ul li a:hover {
  color: blue;
}
</style>
