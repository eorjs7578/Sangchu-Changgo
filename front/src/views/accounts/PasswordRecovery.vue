<template>
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
  </template>
  
  <script>
  export default {
    data() {
      return {
        email: '',
        verificationCode: '',
        timer: '3:31'  // 타이머 예시
      };
    },
    methods: {
      sendVerificationCode() {
        // 인증번호 전송 로직
        console.log('인증번호 전송:', this.email);
      },
      verifyCode() {
        // 인증번호 확인 로직
        console.log('인증번호 확인:', this.verificationCode);
      },
      recoverPassword() {
        // 비밀번호 복구 로직
        console.log('비밀번호 복구 이메일 전송:', this.email);
      }
    }
  };
  </script>
  
  <style scoped>
  .password-recovery-container {
    width: 100%;
    max-width: 400px;
    margin: 0 auto;
    padding: 2rem;
    text-align: center;
    background-color: white;
  }
  
  .password-recovery-container h2 {
    font-size: 2rem;
    margin-bottom: 1rem;
  }
  
  .password-recovery-container p {
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
  