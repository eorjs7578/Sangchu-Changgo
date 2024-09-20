<template>
  <div class="main-container">
    <div class="signup-wrapper">
      <div class="signup-container">
        <h2>Sign Up</h2>
        <p>회원가입 후 다양한 기능을 이용하세요.</p>
        <form @submit.prevent="submitSignup">
          <!-- 이름 필드 -->
          <label for="name">Name</label>
          <input type="text" id="name" placeholder="Your name" v-model="name" />
    
          <!-- 닉네임 필드 -->
          <label for="nickname">Nickname</label>
          <input type="text" id="nickname" placeholder="Your nickname" v-model="nickname" />
          <span v-if="nicknameExists" class="error-text">사용 중인 닉네임 입니다</span>
    
          <!-- 이메일, 인증 -->
          <label for="email">Email</label>
          <div class="email-verification">
            <input type="email" id="email" placeholder="example@example.com" v-model="email" />
            <button type="button" @click="sendEmailVerification">인증번호 전송</button>
          </div>
    
          <!-- 인증번호 확인 -->
          <div class="verification-code">
            <input type="text" placeholder="000000" v-model="verificationCode" />
            <div class="verification-wrapper">
              <button type="button" @click="verifyCode">인증번호 확인</button>
              <span class="timer">3:31</span>
            </div>
          </div>
          
          <!-- 비밀번호 -->
          <label for="password">Password</label>
          <input type="password" id="password" placeholder="Your password" v-model="password" />
    
          <!-- 비밀번호 확인 -->
          <label for="repeatPassword">Repeat password</label>
          <input type="password" id="repeatPassword" placeholder="Repeat" v-model="repeatPassword" />
    
          <!-- 확인버튼 -->
          <button type="submit">Sign Up</button>
        </form>
    
        <p>이미 계정이 있으신가요? <router-link to="/login">Log In</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const name = ref('');
const nickname = ref('');
const email = ref('');
const password = ref('');
const repeatPassword = ref('');
const verificationCode = ref('');
const nicknameExists = ref(false);

const submitSignup = () => {
  if (password.value !== repeatPassword.value) {
    alert('Passwords do not match');
    return;
  }
  console.log('Signing up with', name.value, nickname.value, email.value);
};

const sendEmailVerification = () => {
  console.log('Sending email verification to', email.value);
};

const verifyCode = () => {
  console.log('Verifying code', verificationCode.value);
};
</script>

<style scoped>
/* 전체 레이아웃을 잡는 컨테이너 */
.main-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  background-color: #f3f4f6;
}

.signup-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 1200px;
  padding: 1rem;
  box-sizing: border-box;
}

.signup-container {
  width: 100%;
  max-width: 600px;
  padding: 2rem;
  background-color: white;
}

.signup-container h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
  text-align: center;
}

.signup-container p {
  text-align: center;
  margin-bottom: 1.5rem;
}

.signup-container form {
  width: 100%;
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
  transition: background-color 0.3s ease;
}

.signup-container button:hover {
  background-color: #218838;
}

.email-verification,
.verification-code {
  display: flex;
  justify-content: space-between;
  margin-bottom: 1rem;
}

.email-verification input,
.verification-code input {
  flex: 1;
  margin-right: 0.5rem;
  height: 40px; /* 입력 칸과 버튼의 높이를 동일하게 설정 */
}

.email-verification button,
.verification-code button {
  padding: 0 10px;
  height: 40px; /* 버튼 높이를 입력 칸 높이와 동일하게 설정 */
  white-space: nowrap;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.verification-wrapper {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  margin-left: 0.5rem;
}

.timer {
  font-size: 0.8rem;
  color: #888;
  margin-top: 0.2rem; /* 타이머를 버튼과 붙이기 */
}

.error-text {
  color: red;
  font-size: 0.9rem;
  margin-top: -0.5rem;
  margin-bottom: 0.5rem;
}
</style>
