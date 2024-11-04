<template>
  <div class="container">
    <div class="user-info">
      <h2>{{ store.userInfo.nickname }} 님</h2>
      <div class="email-container">
        <span>{{ store.userInfo.email }}</span>
        <button @click="toggleEditForm" class="edit-button">
          <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
            <path d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"></path>
            <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z"></path>
          </svg>
        </button>
      </div>
    </div>

    <div v-if="showEditForm" class="edit-form">
      <h3>정보 변경</h3>
      <div class="form-group">
        <label for="email">이메일 변경</label>
        <div class="input-group">
          <input type="email" id="email" v-model="newEmail" required :disabled="isEmailLocked">
          <button v-if="!isEmailLocked" @click="handleGetAuthCode">이메일 인증</button>
          <button v-else @click="changeEmail">이메일 변경</button>
        </div>
        <div v-if="isEmailSend">
          <input type="text" v-model="authCode" placeholder="인증 코드 입력" />
          <button @click="handleCheckAuthCode">인증 코드 확인</button>
          <p v-if="validationTime > 0">인증번호 유효 시간: {{ validationTime }}초</p>
          <p v-else>인증 시간이 만료되었습니다. 다시 인증번호를 요청해주세요.</p>
        </div>
      </div>
      <div class="form-group">
        <label for="nickname">닉네임 변경</label>
        <div class="input-group">
          <input type="text" id="nickname" v-model="newNickname" required>
          <button @click="changeNickname">변경</button>
        </div>
      </div>
      <div class="form-group">
        <label for="password">비밀번호 변경</label>
        <div class="input-group">
          <input type="password" id="oldPassword" v-model="oldPassword" placeholder="현재 비밀번호" required>
          <input type="password" id="newPassword" v-model="newPassword" placeholder="새 비밀번호" required>
          <button @click="changePassword">변경</button>
        </div>
      </div>
    </div>

    <div v-if="!showEditForm" class="container-tabs">
      <div class="tabs">
        <button
          v-for="tab in tabs"
          :key="tab.id"
          @click="currentTab = tab.id"
          :class="['tab-button', { active: currentTab === tab.id }]"
        >
          {{ tab.name }}
        </button>
      </div>

      <div v-if="currentTab === 'posts'" class="posts">
        <div v-for="post in posts" :key="post.id" class="post">
          <h3>{{ post.title }}</h3>
          <p class="gallery">{{ post.gallery }}</p>
          <p>{{ post.content }}</p>
        </div>
      </div>

      <div v-else-if="currentTab === 'areas'" class="empty-state">
        즐겨찾기한 상권이 없습니다.
      </div>

      <div v-else-if="currentTab === 'franchises'" class="empty-state">
        즐겨찾기한 프랜차이즈가 없습니다.
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { useAccountStore } from '@/stores/useAccountStore.js';
import { api } from '@/lib/api.js'; // API 요청을 위한 라이브러리 사용
import {
    isUsernameValidated,
    isEmailValidated,
    isNicknameValidated,
    isPasswordValidated,
    isNicknameDuplicated,
    isUsernameDuplicated,
    checkAuthCode,
    getAuthCode,
    signUp, login,
    updateEmail
  } from "@/util/AuthenticationUtil.js";

const store = useAccountStore();

const user = ref({
  name: store.userInfo.nickname || 'OOO 님',
  email: store.userInfo.email || 'OOO@gmail.com',
});

const tabs = [
  { id: 'posts', name: '내가 쓴 글' },
  { id: 'areas', name: '즐겨찾기한 상권' },
  { id: 'franchises', name: '즐겨찾기한 프랜차이즈' },
];

const currentTab = ref('posts');
const showEditForm = ref(false);
const newEmail = ref('');
const newNickname = ref('');
const oldPassword = ref(''); // 현재 비밀번호 필드
const newPassword = ref('');
const authCode = ref('');
const isEmailSend = ref(false); // 이메일 발송 여부
const isEmailChecked = ref(false);
const validationTime = ref(0); // 인증 시간 제한
const isEmailLocked = ref(false)

let timer = null;

const toggleEditForm = () => {
  showEditForm.value = !showEditForm.value;
  if (showEditForm.value) {
    newEmail.value = user.value.email;
    newNickname.value = user.value.name.replace(' 님', '');
    oldPassword.value = '';
    newPassword.value = '';
  }
};

// 이메일 인증번호 발송
function handleGetAuthCode() {
    if (isEmailValidated(email.value)) {
      getAuthCode(email.value)
          .then(response => {
          isEmailSend.value = true;
          validationTime.value = 300;
          })
          .catch(err =>
              alert(err.response.data)
          )
    }
  }

// 이메일 인증번호 확인
function handleCheckAuthCode() {

if (!isEmailSend.value) {
  alert("인증 코드를 먼저 받아주세요.")
  return;
}

checkAuthCode(email.value, authCode.value)
    .then(isVerified => {
      if (isVerified) {
        alert("인증되었습니다.")
        isEmailChecked.value = true;
        isEmailSend.value = false;
        isEmailLocked.value = true;
      }
      else {
        alert("인증 번호를 다시 한 번 확인해주세요.")
      }
    })
}


// 이메일 변경 API 호출 함수
const changeEmail = () => {
  if (isEmailChecked) {
  updateEmail(newEmail.value, authCode.value) // 새로운 이메일과 인증 코드를 전달
    .then(success => {
      if (success) {
        alert('이메일이 성공적으로 변경되었습니다.');
        store.userInfo.email = newEmail.value; // 스토어에 저장된 이메일 업데이트
        window.location.reload();
      } else {
        alert('이메일 변경 실패. 다시 시도해 주세요.');
      }
    })
    .catch((error) => {
      console.log('이메일 변경 실패:', error);
    });
  }
};

// 타이머: 인증 시간이 지나면 초기화
watch([isEmailSend, validationTime], ([newIsEmailSend, newValidationTime]) => {
  if (timer) {
    clearInterval(timer); // 기존 타이머 삭제
  }
  if (newIsEmailSend && newValidationTime > 0) {
    timer = setInterval(() => {
      validationTime.value -= 1;
    }, 1000); // 1초마다 감소
  } else if (newValidationTime === 0) {
    isEmailSend.value = false; // 시간이 만료되면 이메일 발송 상태 초기화
    clearInterval(timer);
    timer = null;
  }
});

// 닉네임 변경 함수
const changeNickname = () => {
  api.post('/api/members/update/nickname', {
    nickname: newNickname.value,
  })
    .then(() => {
      user.value.name = newNickname.value + ' 님';
      store.userInfo.nickname = newNickname.value; // 스토어의 닉네임 업데이트
      showEditForm.value = false;
    })
    .catch((error) => {
      console.log('닉네임 변경 실패:', error);
    });
};

// 비밀번호 변경 함수
const changePassword = () => {
  api.post('/api/members/update/password', {
    old_password: oldPassword.value,
    new_password: newPassword.value,
  })
    .then(() => {
      console.log('비밀번호 변경 성공');
      oldPassword.value = '';
      newPassword.value = '';
      showEditForm.value = false;
    })
    .catch((error) => {
      console.log('비밀번호 변경 실패:', error);
    });
};

</script>

<style scoped>
/* 기존 스타일 유지 */
.container {
  min-height: 100vh;
  background-color: #f3f4f6;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.container-tabs {
  width: 100%;
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
}

.user-info {
  margin-bottom: 1.5rem;
  width: 100%;
  text-align: left;
  max-width: 700px;
}

.user-info h2 {
  font-size: 1.5rem;
  font-weight: bold;
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.email-container {
  display: flex;
  justify-content: flex-start;
  align-items: center;
  color: #4b5563;
}

.edit-button {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.25rem;
  margin-left: 0.5rem;
  color: #9ca3af;
}

.edit-button:hover {
  color: #6b7280;
}

.edit-form {
  margin-bottom: 1.5rem;
  width: 100%;
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
}

.edit-form h3 {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 1rem;
  text-align: left;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  font-size: 0.875rem;
  font-weight: 500;
  color: #374151;
  margin-bottom: 0.5rem;
}

.input-group {
  display: flex;
}

.input-group input {
  flex: 1;
  padding: 0.5rem;
  border: 1px solid #d1d5db;
  border-radius: 0.25rem 0 0 0.25rem;
  font-size: 0.875rem;
}

.input-group button {
  padding: 0.5rem 1rem;
  background-color: #f3f4f6;
  border: 1px solid #d1d5db;
  border-left: none;
  border-radius: 0 0.25rem 0.25rem 0;
  font-size: 0.875rem;
  color: #4b5563;
  cursor: pointer;
}

.tabs {
  display: flex;
  border-bottom: 1px solid #e5e7eb;
  margin-bottom: 1.5rem;
  width: 100%;
  justify-content: flex-start;
  max-width: 700px;
  margin-left: auto;
  margin-right: auto;
}

.tab-button {
  padding: 0.5rem 1rem;
  font-size: 0.875rem;
  font-weight: 500;
  color: #4b5563;
  background: none;
  border: none;
  cursor: pointer;
}

.tab-button.active {
  color: #2563eb;
  border-bottom: 2px solid #2563eb;
}

.posts {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  width: 100%;
}

.post {
  background-color: #f9fafb;
  padding: 1.5rem;
  border-radius: 0.5rem;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.post h3 {
  font-size: 1.25rem;
  font-weight: 600;
  color: #1f2937;
  margin-bottom: 0.5rem;
}

.post .gallery {
  font-size: 0.875rem;
  color: #4b5563;
  margin-bottom: 0.5rem;
}

.empty-state {
  text-align: center;
  color: #6b7280;
  padding: 2rem 0;
}
</style>
