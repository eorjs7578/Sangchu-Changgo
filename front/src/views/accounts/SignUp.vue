<template>
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
          <button type="button" @click="verifyCode">인증번호 확인</button>
          <span class="timer">3:31</span> <!-- 타이머 추가 -->
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
  </template>
  
  <script>
  export default {
    data() {
      return {
        name: '',
        nickname: '',
        email: '',
        password: '',
        repeatPassword: '',
        verificationCode: '',
        nicknameExists: false // 닉네임 중복 여부
      };
    },
    methods: {
      // 회원가입 처리 로직
      submitSignup() {
        if (this.password !== this.repeatPassword) {
          alert('Passwords do not match');
          return;
        }
        // 회원가입 처리 로직 추가
        console.log('Signing up with', this.name, this.nickname, this.email);
      },
      // 이메일 인증번호 전송 로직
      sendEmailVerification() {
        console.log('Sending email verification to', this.email);
        // 이메일 전송 로직 추가
      },
      // 인증번호 확인 로직
      verifyCode() {
        console.log('Verifying code', this.verificationCode);
        // 인증번호 확인 로직 추가
      }
    }
  };
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
  