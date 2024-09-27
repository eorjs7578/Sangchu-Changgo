<template>
  <div class="community-interface">
    <h1 class="title">{{ isEdit ? "게시글 수정" : "새 게시글 작성" }}</h1>

    <!-- 게시판 선택 -->
    <!-- <div v-if="!isEdit" class="mb-4">
      <label for="board" class="block text-sm font-medium text-gray-700 mb-2">게시판 선택</label>
      <select v-model="boardId" class="search-input">
        <option disabled value="">게시판을 선택하세요</option>
        <option v-for="board in boardList" :key="board.id" :value="board.id">{{ board.name }}</option>
      </select>
    </div> -->

    <!-- 제목 입력 -->
    <div class="mb-4">
      <label for="title" class="block text-sm font-medium text-gray-700 mb-2">제목</label>
      <input
        id="title"
        v-model="title"
        type="text"
        required
        class="search-input"
        placeholder="제목을 입력하세요"
      />
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
        @click="submitPost"
      >
        {{ isEdit ? "수정 완료" : "작성 완료" }}
      </button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { createPost, getPostDetail, updatePost } from '@/api/forum'; // API 함수 가져오기

export default {
  setup() {
    const route = useRoute();
    const router = useRouter();
    const postId = route.params.postId;  // postId가 있으면 수정, 없으면 새 글 작성
    const isEdit = !!postId;  // postId가 있으면 true, 없으면 false로 구분

    const title = ref('');
    const content = ref('');
    const boardId = ref('');  // 선택한 게시판 ID
    const boardList = ref([   // 게시판 목록 (예시)
      { id: 1, name: '상권 게시판' },
      { id: 2, name: '프랜차이즈 게시판' },
      { id: 3, name: '상권 매물 게시판' },
    ]);

    // 게시글 수정 시 기존 데이터 가져오기
    const fetchPost = () => {
      if (isEdit) {
        getPostDetail(postId, 
          (response) => {
            title.value = response.data.title;
            content.value = response.data.content;
            boardId.value = 80;  // 수정 시 기존 게시판 ID 불러오기 (필요 시)
          },
          (error) => {
            console.error('게시글을 불러오는 중 에러 발생:', error);
          }
        );
      }
    };

    onMounted(fetchPost);  // 페이지 로드 시 데이터 가져오기

    const submitPost = () => {
      const payload = {
        board_id: 80,  // 게시판 ID
        title: title.value,       // 제목
        content: content.value    // 내용
      };

      console.log('보내는 데이터:', payload);  // 확인을 위한 로그

      // if (!payload.board_id) {
      //   alert('게시판을 선택하세요.');
      //   return;
      // }

      if (isEdit) {
        // 게시글 수정 처리
        updatePost(postId, payload, 
          (response) => {
            console.log('게시글 수정 성공:', response);
            router.push(`/PostDetail/${postId}`);  // 수정 후 게시글 상세 페이지로 이동
          },
          (error) => {
            console.error('게시글 수정 중 에러 발생:', error);
          }
        );
      } else {
        // 새 게시글 작성 처리
        createPost(payload, 
          (response) => {
            console.log('게시글 작성 성공:', response);
            router.push('/PostList');  // 작성 후 게시글 목록으로 이동
          },
          (error) => {
            console.error('게시글 작성 중 에러 발생:', error);
          }
        );
      }
    };

    return {
      title,
      content,
      boardId,
      boardList,
      submitPost,
      isEdit,
    };
  },
};
</script>

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
