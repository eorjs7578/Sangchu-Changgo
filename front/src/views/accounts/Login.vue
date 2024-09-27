<template>
  <div class="login-container">
    <h2>Log In</h2>
    <p>로그인 후 다양한 기능을 이용하세요.</p>
    <form @submit.prevent="handleLogin">
      <label for="username">아이디</label>
      <input type="username" id="username" placeholder="아이디" v-model="username" />

      <label for="password">비밀번호</label>
      <input type="password" id="password" placeholder="비밀번호" v-model="password" />

      <p><router-link to="/members/password">비밀번호가 생각나지 않으신가요?</router-link></p>
      <button type="submit">Log In</button>
    </form>

    <p>아직 회원이 아니신가요? <router-link to="/members/signup">Sign up</router-link></p>

    <!-- 외부 로그인 버튼 -->
    <div class="external-login-buttons">
      <img
        src="@/assets/img/카카오 로그인.png"
        alt="Kakao Login"
        class="kakao-login-btn"
        @click="kakaoLogin"
      />
      <img
        src="@/assets/img/btnG_축약형.png"
        alt="Naver Login"
        class="naver-login-btn"
        @click="naverLogin"
      />
    </div>
  </div>
</template>

<script setup>
  import { ref } from "vue";
  import { useRouter } from "vue-router";
  import { login, kakaoLogin, naverLogin } from "@/util/AuthenticationUtil.js";
  import {useAccountStore} from "@/stores/useAccountStore.js";

  const router = useRouter();
  const username = ref("");
  const password = ref("");

  const store = useAccountStore();

  function handleLogin() {
    login(username.value, password.value)
        .then(() => {
          store.isAuthenticated = true;
          localStorage.setItem("isLoggedIn", "true"); // 로그인 상태 저장
          router.push("/").then(() => {
            window.location.reload();
          });
        })
        .catch((err) => {
          alert("로그인에 실패했습니다.");
          console.log(err)
        });
  }
</script>

<style scoped>
.login-container {
  width: 80%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 2rem;
}

.login-container h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.login-container form {
  width: 100%;
  max-width: 400px;
  display: flex;
  flex-direction: column;
}

.login-container label {
  margin-bottom: 0.5rem;
}

.login-container input {
  padding: 0.8rem;
  margin-bottom: 1rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.login-container button {
  padding: 0.8rem;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.login-container button:hover {
  background-color: #218838;
}

.external-login-buttons {
  display: flex;
  justify-content: center; /* 버튼들을 가운데 정렬 */
  gap: 20px; /* 버튼 간의 간격 조정 */
  margin-top: 1rem;
}

.kakao-login-btn,
.naver-login-btn {
  width: 100px; /* 버튼의 너비 설정 */
  height: auto; /* 비율을 유지하면서 높이 자동 설정 */
  cursor: pointer;
}
</style>
