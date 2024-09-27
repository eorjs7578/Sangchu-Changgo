<script setup>
import { ref } from 'vue'
import { createPost } from '@/api/forum'  // 게시글 생성 API 호출 함수

// 제목과 내용에 대한 ref 생성
const subject = ref('')
const content = ref('')

// URL에서 boardId 추출
// const route = useRoute();
// const boardId = route.params.boardId;  // URL에서 boardId 추출


// 폼 제출 함수
const submitForm = () => {

  // 요청 본문 데이터 생성
  const postData = {
    // board_id: selectedBoard.value,  // 게시판 ID
    board_id: 3001492,  // 게시판 ID
    title: subject.value,  // 제목
    content: content.value  // 내용
  };

  // 게시글 작성 요청 보내기
  createPost(postData,
    (response) => {
      console.log('게시글 작성 성공:', response);    
      
    // Location 헤더에서 새 게시글의 URL 확인
    const location = response.headers['location'] || response.headers['Location'];
    console.log('새로 생성된 게시글 URL:', response.headers);

      // 폼 초기화
      subject.value = '';
      content.value = '';
    },
    (error) => {
      console.error('게시글 작성 중 에러 발생:', error);
    }
  );
};
</script>

<template>
  <div class="community-interface">
    <h1 class="title">글쓰기</h1>

    <!-- 제목 입력 -->
    <div class="mb-4">
      <label for="subject" class="block text-sm font-medium text-gray-700 mb-2">제목</label>
      <input
        id="subject"
        v-model="subject"
        type="text"
        required
        class="search-input"
        placeholder="제목을 입력하세요"
      >
    </div>

    <!-- 내용 입력 -->
    <div class="mb-4">
      <label for="content" class="block text-sm font-medium text-gray-700 mb-2">내용</label>
      <textarea
        id="content"
        v-model="content"
        rows="6"
        required
        class="search-input"
        placeholder="내용을 입력하세요"
      ></textarea>
    </div>

    <!-- 제출 버튼 -->
    <div class="flex justify-end">
      <button
        type="submit"
        class="search-button"
        @click="submitForm"
      >
        제출하기
      </button>
    </div>
  </div>
</template>

<style scoped>
.community-interface {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.title {
  font-size: 24px;
  margin-bottom: 20px;
}

.search-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.search-button {
  padding: 10px 20px;
  background-color: #f0f0f0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.mb-4 {
  margin-bottom: 20px;
}
</style>
