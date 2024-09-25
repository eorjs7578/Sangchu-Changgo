<template>
  <div class="login-container">
    <h2>Log In</h2>
    <p>로그인 후 다양한 기능을 이용하세요.</p>
    <form @submit.prevent="login">
      <label for="username">아이디</label>
      <input type="username" id="username" placeholder="아이디" v-model="username" />

      <label for="password">Password</label>
      <input type="password" id="password" placeholder="Your password" v-model="password" />

      <p><router-link to="/passwordrecovery">비밀번호가 생각나지 않으신가요?</router-link></p>
      <button type="submit">Log In</button>
    </form>

    <p>아직 회원이 아니신가요? <router-link to="/signup">Sign up</router-link></p>

    <!-- 외부 로그인 버튼 -->
    <div class="external-login-buttons">
      <button class="kakao-btn" @click="kakaoLogin()">Kakao Login</button>
      <button class="naver-btn" @click="naverLogin()">Naver Login</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { api } from "@/lib/api.js";
import { useRouter } from "vue-router";

  const router = useRouter();
  const username = ref("");
  const password = ref("");

  const kakaoLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/kakao?redirect_uri=http://localhost:5173&mode=login";
  }
  const naverLogin = () => {
    window.location.href = "http://localhost:8080/oauth2/authorization/naver?redirect_uri=http://localhost:5173&mode=login";
  }

  function login() {
    api.post("/api/members/login", {
      username: username.value,
      password: password.value,
    })
        .then(response => {
          router.push("/")
        })
        .catch(err => console.log(err))
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
  justify-content: space-around;
  margin-top: 1rem;
}

.kakao-btn {
  background-color: #fee500;
  color: black;
  padding: 0.8rem;
  border-radius: 4px;
  border: none;
  cursor: pointer;
}

.naver-btn {
  background-color: #03c75a;
  color: white;
  padding: 0.8rem;
  border-radius: 4px;
  border: none;
  cursor: pointer;
}
</style>
