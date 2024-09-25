<template>
    <div class="signup-container">
      <h2>Sign Up</h2>
      <p>회원가입 후 다양한 기능을 이용하세요.</p>
      <form @submit.prevent="signUp">
        <!-- 이름 필드 -->
        <label for="username">아이디</label>
        <input type="text" id="username" placeholder="아이디" v-model="username" />
  
        <!-- 닉네임 필드 -->
        <label for="nickname">닉네임</label>
        <input type="text" id="nickname" placeholder="닉네임" v-model="nickname" />
        <span v-if="isNicknameExists" class="error-text">사용 중인 닉네임 입니다</span>
  
        <!-- 이메일, 인증 -->
        <label for="email">이메일</label>
        <div class="email-verification">
          <input type="email" id="email" placeholder="example@example.com" v-model="email" />
          <button type="button" @click="getAuthCode">인증번호 전송</button>
        </div>
  
        <!-- 인증번호 확인 -->
        <div class="verification-code">
          <input type="text" placeholder="이메일 인증" v-model="authCode" />
          <button type="button" @click="verifyAuthCode">인증번호 확인</button>
          <span class="timer">3:31</span> <!-- 타이머 추가 -->
        </div>
  
        <!-- 비밀번호 -->
        <label for="password">비밀번호</label>
        <input type="password" id="password" placeholder="비밀번호" v-model="password" />

        <!-- 비밀번호 확인 -->
        <label for="passwordConfirm">비밀번호 확인</label>
        <input type="password" id="passwordConfirm" placeholder="비밀번호 확인" v-model="passwordConfirm" />
  
        <!-- 확인버튼 -->
        <button type="submit">Sign Up</button>
      </form>
  
      <p>이미 계정이 있으신가요? <router-link to="/login">Log In</router-link></p>
    </div>
  </template>
  
  <script setup>
  import { ref } from "vue";
  import { api } from "@/lib/api.js";
  import { useRouter } from "vue-router";

  const router = useRouter();

  const username = ref("");
  const password = ref("");
  const email = ref("");
  const passwordConfirm = ref("");
  const nickname = ref("");
  const authCode = ref("");
  const isEmailChecked = ref(false);
  const isNicknameExists = ref(false);

  // 이메일 정규 표현식
  function validateEmail(email) {
    return /^[A-Za-z0-9]+@(.+)$/i.test(email.value);
  }

  // 닉네임 정규 표현식 (10글자 이하, 한글 자모 단독 불가)
  function validateNickname(nickname) {
    return /^(?!.*[ㄱ-ㅎㅏ-ㅣ])[A-Za-z0-9가-힣]{1,10}$/.test(nickname.value);
  }

  // 8자 이상, 숫자1, 특문1 포함
  function validatePassword(password) {
    return /^(?=.*[0-9])(?=.*[a-z])(?=.*[^a-zA-Z0-9])(?=\S+$).{8,}$/.test(password.value);
  }

  function signUp() {
    if (!validateEmail(email) || !validateNickname(nickname) || !validatePassword(password.value) || !(password.value == passwordConfirm.value)) {
      return alert("예외 처리 중")
    }

    else {
    api.post("/api/members/signup", {
      nickname: nickname.value,
      username: username.value,
      password: password.value,
      email: email.value,
    })
        // 완성되면 메인 페이지로
        .then(() => router.push("/"))
        // 오류 시 에러 메시지 출력
        .catch(err => console.log(err))
  }}

  function verifyAuthCode() {
    api.post("/api/members/auth-email", {
      email,
      auth_code: authCode.value
    })
        .then(response => {
          alert("인증되었습니다.")
          isEmailChecked.value = true
        })
  }

  function getAuthCode() {
    api.get("/api/members/auth-email", {params: {
      email
      }})
        .then(response => {
          alert("인증번호가 이메일로 발송되었습니다.")
        })
  }

  </script>
  
  <style scoped>
  .signup-container {
    width: 80%;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    padding: 2rem;
  }
  
  .signup-container h2 {
    font-size: 2rem;
    margin-bottom: 1rem;
  }
  
  .signup-container form {
    width: 100%;
    max-width: 400px;
    display: flex;
    flex-direction: column;
  }
  
  .signup-container label {
    margin-bottom: 0.5rem;
  }
  
  .signup-container input {
    padding: 0.8rem;
    margin-bottom: 1rem;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  
  .signup-container button {
    padding: 0.8rem;
    background-color: #28a745;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  .signup-container button:hover {
    background-color: #218838;
  }
  
  /* 이메일 인증 관련 스타일 */
  .email-verification {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1rem;
  }
  
  .verification-code {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .timer {
    font-size: 0.8rem;
    color: #888;
  }
  
  .error-text {
    color: red;
    font-size: 0.9rem;
  }
  </style>
  