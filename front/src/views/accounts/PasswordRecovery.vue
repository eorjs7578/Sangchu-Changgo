<template>
  <div class="main-container">
    <div class="password-recovery-wrapper">
      <div class="password-recovery-container">
        <h2>Password recovery</h2>
        <p>이메일 입력 후 해당 이메일로 임시 비밀번호를 확인하세요.</p>
        
        <!-- Email Input and Verification -->
        <form @submit.prevent="recoverPassword">
          <div class="input-group">
            <input type="email" id="email" placeholder="example@example.com" v-model="email" />
            <button type="button" class="verify-btn" @click="sendVerificationCode">인증번호 전송</button>
          </div>
          
          <!-- Verification Code Input -->
          <div class="input-group">
            <input type="text" id="verificationCode" placeholder="000000" v-model="verificationCode" />
            <button type="button" class="verify-btn" @click="verifyCode">인증번호 확인</button>
            <span class="timer">{{ timer }}</span>
          </div>
          
          <!-- Recover Password Button -->
          <button type="submit" class="recover-btn">Recover password</button>
        </form>
    
        <!-- Back to login link -->
        <router-link to="/login" class="back-to-login">Back to login</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const email = ref('');
const verificationCode = ref('');
const timer = ref('3:31');  // 타이머 예시

const sendVerificationCode = () => {
  console.log('인증번호 전송:', email.value);
};

const verifyCode = () => {
  console.log('인증번호 확인:', verificationCode.value);
};

const recoverPassword = () => {
  console.log('비밀번호 복구 이메일 전송:', email.value);
};
</script>

<style scoped>
/* 전체 레이아웃 */
.main-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  background-color: #f3f4f6;
}

.password-recovery-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  max-width: 1200px;
  padding: 1rem;
  box-sizing: border-box;
}

.password-recovery-container {
  width: 100%;
  max-width: 600px;
  padding: 2rem;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
}

.password-recovery-container h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
  text-align: center;
}

.password-recovery-container p {
  text-align: center;
  margin-bottom: 2rem;
  font-size: 1rem;
}

.input-group {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
}

.input-group input {
  width: 65%;
  padding: 0.8rem;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.verify-btn {
  width: 30%;
  padding: 0.8rem;
  background-color: #ccc;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.verify-btn:hover {
  background-color: #bbb;
}

.timer {
  font-size: 0.9rem;
  margin-left: 0.5rem;
}

.recover-btn {
  width: 100%;
  padding: 0.8rem;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.recover-btn:hover {
  background-color: #218838;
}

.back-to-login {
  display: block;
  margin-top: 1rem;
  color: blue;
  text-decoration: none;
}

.back-to-login:hover {
  text-decoration: underline;
}
</style>
