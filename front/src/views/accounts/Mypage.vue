<template>
    <div class="container">
      <div class="user-info">
        <h2>{{ store.userInfo.nickname }}</h2>
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
            <input type="email" id="email" v-model="newEmail" required>
            <button @click="changeEmail">이메일 인증</button>
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
            <input type="password" id="password" v-model="newPassword" required>
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
  import { ref } from 'vue'
  import { useAccountStore } from "@/stores/useAccountStore.js"

  const store = useAccountStore();
  
  const user = ref({
  name: store.userInfo.nickname || 'OOO 님',
  email: store.userInfo.email || 'OOO@gmail.com',
});
  
  const tabs = [
    { id: 'posts', name: '내가 쓴 글' },
    { id: 'areas', name: '즐겨찾기한 상권' },
    { id: 'franchises', name: '즐겨찾기한 프랜차이즈' }
  ]
  
  const currentTab = ref('posts')
  const showEditForm = ref(false)
  const newEmail = ref('')
  const newNickname = ref('')
  const newPassword = ref('')
  
  const posts = ref([
    { id: 1, title: '첫 번째 글', gallery: '인동 갤러리', content: '이것은 첫 번째 글의 요약입니다.' },
    { id: 2, title: '두 번째 글', gallery: '맛집 갤러리', content: '이것은 두 번째 글의 요약입니다.' },
    { id: 3, title: '세 번째 글', gallery: '여행 갤러리', content: '이것은 세 번째 글의 요약입니다.' },
  ])
  
  const toggleEditForm = () => {
    showEditForm.value = !showEditForm.value
    if (showEditForm.value) {
      newEmail.value = user.value.email
      newNickname.value = user.value.name.replace(' 님', '')
      newPassword.value = ''
    }
  }
  
  const changeEmail = () => {
    user.value.email = newEmail.value
    showEditForm.value = false
  }
  
  const changeNickname = () => {
    user.value.name = newNickname.value + ' 님'
    showEditForm.value = false
  }
  
  const changePassword = () => {
    console.log('Password changed to:', newPassword.value)
    newPassword.value = ''
    showEditForm.value = false
  }
  </script>
  
  <style scoped>
  .container {
  min-height: 100vh;
  background-color: #f3f4f6;
  padding: 2rem;
  display: flex;
  flex-direction: column;
  align-items: center; /* 컨텐츠를 중앙으로 정렬 */
}

.container-tabs {
  width: 100%;
  max-width: 700px; /* Set the maximum width to 700px */
  margin-left: auto;
  margin-right: auto; /* Center the container */
}

.user-info {
  margin-bottom: 1.5rem;
  width: 100%;
  text-align: left; /* 사용자 정보 가운데 정렬 */
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
  justify-content: flex-start; /* 이메일과 수정 버튼을 중앙 정렬 */
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
  max-width: 700px; /* 입력 폼의 너비를 제한 */
  margin-left: auto;
  margin-right: auto; /* Center the form */
}

.edit-form h3 {
  font-size: 1.25rem;
  font-weight: 600;
  margin-bottom: 1rem;
  text-align: left; /* "정보 변경" 텍스트 가운데 정렬 */
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
  justify-content: flex-start; /* 탭 버튼을 중앙 정렬 */
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
  