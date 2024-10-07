<template>
  <div class="main-container">
    <div class="password-recovery-wrapper">
      <div class="password-recovery-container">
        <h2>Password Recovery</h2>
        <p>이메일 입력 후 해당 이메일로 임시 비밀번호를 확인하세요.</p>

        <form @submit.prevent="recoverPassword">
          <!-- 이메일 입력 및 인증번호 전송 버튼 -->
          <div class="input-group">
            <input
              type="email"
              id="email"
              placeholder="example@example.com"
              v-model="email"
              :disabled="isEmailChecked"
              class="rounded-input"
            />
            <button
              type="button"
              class="verify-btn"
              @click="handleGetAuthCode"
              :disabled="isEmailChecked || isEmailSend"
              :class="{'btn-secondary': isEmailChecked || isEmailSend, 'btn-primary': !isEmailChecked && !isEmailSend }"
            >
              {{ isEmailChecked ? '인증 완료' : '인증번호 전송' }}
            </button>
          </div>

          <!-- 인증번호 입력 및 확인 -->
          <div class="input-group" v-if="isEmailSend">
            <input
              type="text"
              id="verificationCode"
              placeholder="000000"
              v-model="verificationCode"
              :disabled="isEmailChecked"
              class="code-input rounded-input"
            />
            <span class="timer">{{ formatTime(validationTime) }}</span>
            <button
              type="button"
              class="verify-btn"
              @click="verifyCode"
              :disabled="isEmailChecked || !verificationCode"
              :class="{'btn-secondary': isEmailChecked, 'btn-primary': !isEmailChecked && verificationCode}"
            >
              인증번호 확인
            </button>
          </div>

          <!-- 비밀번호 복구 버튼 -->
          <button
            type="submit"
            class="recover-btn"
            :disabled="!isEmailChecked"
          >
            Recover password
          </button>
        </form>

        <!-- 로그인 페이지로 돌아가기 -->
        <router-link to="/members/login" class="back-to-login">
          Back to login
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useRouter } from 'vue-router';  // 라우터 사용
import { getAuthCode, isEmailValidated, checkAuthCode } from "@/util/AuthenticationUtil.js";

// 상태 변수 선언
const email = ref('');
const verificationCode = ref('');
const isEmailSend = ref(false);
const validationTime = ref(300); // 5분 타이머
const isEmailChecked = ref(false);

const router = useRouter();  // 라우터 객체 생성

// 타이머 포맷 함수
const formatTime = (time) => {
  const minutes = Math.floor(time / 60);
  const seconds = time % 60;
  return `${minutes}:${seconds.toString().padStart(2, '0')}`;
};

// 타이머 동작 함수
let timer = null;
const startTimer = () => {
  timer = setInterval(() => {
    if (validationTime.value > 0) {
      validationTime.value -= 1;
    } else {
      clearInterval(timer);
      isEmailSend.value = false;
    }
  }, 1000);
};

// 이메일 인증번호 발송 처리
const handleGetAuthCode = () => {
  if (isEmailValidated(email.value)) {
    if (!window.confirm("사용하실 수 있는 이메일 입니다. \n사용하시겠습니까?")) {
      return;
    }
    alert("이메일이 발송되었습니다.");
    isEmailSend.value = true;
    validationTime.value = 300; // 5분으로 타이머 초기화
    startTimer();

    getAuthCode(email.value)
      .then(() => {
        console.log('인증번호 전송 성공');
      })
      .catch(() => {
        alert('인증번호 전송에 실패했습니다.');
      });
  } else {
    alert("유효하지 않은 이메일 입니다. \n이메일을 다시 확인해주세요.");
  }
};

// 이메일 인증번호 확인
const verifyCode = () => {
  checkAuthCode(email.value, verificationCode.value)
    .then(isVerified => {
      if (isVerified) {
        alert("인증되었습니다.");
        isEmailChecked.value = true;
        clearInterval(timer); // 인증 완료 시 타이머 정지
      } else {
        alert("인증번호가 일치하지 않습니다.");
      }
    })
    .catch(() => {
      alert("인증 번호를 다시 한 번 확인해주세요.");
    });
};

// 이메일 시간 제한 타이머
watch([isEmailSend, validationTime], ([newIsEmailSend, newValidationTime]) => {
  if (isEmailChecked.value) {
    newValidationTime = 0;
    clearInterval(timer);
  }
  if (timer) {
    clearInterval(timer);
  }
  if (newIsEmailSend && newValidationTime > 0) {
    timer = setInterval(() => {
      validationTime.value -= 1;
    }, 1000);
  } else if (newValidationTime === 0) {
    isEmailSend.value = false;
    clearInterval(timer);
  }
});

// 비밀번호 복구 처리
const recoverPassword = () => {
  if (isEmailChecked.value) {
    console.log('비밀번호 복구 이메일 전송:', email.value);
    // 비밀번호 복구 처리 후 로그인 페이지로 이동
    alert('비밀번호가 복구되었습니다. 로그인 페이지로 이동합니다.');
    router.push("/members/login");
  } else {
    alert('먼저 이메일 인증을 완료하세요.');
  }
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
  border-radius: 8px;
  outline: none; /* 입력 필드의 기본 테두리 제거 */
}

.input-group .code-input {
  width: 60%; /* 인증번호 입력란 너비 조정 */
}

.verify-btn {
  width: 30%;
  padding: 0.8rem;
  border-radius: 8px;
  cursor: pointer;
  background-color: #28a745; /* 기본 초록색 */
  color: white;
  border: none; /* 기본 테두리 제거 */
  outline: none; /* 기본 포커스 테두리 제거 */
}

.verify-btn.btn-secondary {
  background-color: #6c757d; /* 회색 */
}

.verify-btn:hover {
  background-color: #218838; /* 초록색 hover */
}

.timer {
  font-size: 1rem;
  margin-left: 0.5rem;
}

.recover-btn {
  width: 100%;
  padding: 0.8rem;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 8px;
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
