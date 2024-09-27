<template>
    <div class="post-detail">
      <h1>{{ post.title }}</h1>
      <p><strong>작성자:</strong> {{ post.member_id }}</p>
      <p><strong>내용:</strong> {{ post.content }}</p>
      <p><strong>작성일:</strong> {{ formatDate(post.created_at) }}</p>
      <p><strong>조회수:</strong> {{ post.view }}</p>
  
      <!-- 댓글 목록 -->
      <div class="reply-section">
        <h3>댓글</h3>
        <ul>
          <li v-for="(reply, index) in post.reply" :key="index">
            <p><strong>작성자:</strong> {{ reply.member_id }}</p>
            <p>{{ reply.content }}</p>
            <p><strong>작성일:</strong> {{ formatDate(reply.created_at) }}</p>
          </li>
        </ul>
      </div>
  
      <!-- 작성자와 현재 사용자 ID가 일치하면 수정/삭제 버튼 표시 -->
      <div v-if="post.member_id === currentUserId">
        <button @click="editPost">수정</button>
        <button @click="deletePost">삭제</button>
      </div>
  
      <!-- 목록으로 돌아가기 버튼 -->
      <div class="common-buttons">
        <button @click="fnBackToList">목록으로 돌아가기</button>
      </div>
    </div>
  </template>
  
  <script>
  import { ref, onMounted } from 'vue';
  import { useRoute, useRouter } from 'vue-router'; // Vue Router에서 라우트와 라우터 사용
  import { getPostDetail } from '@/api/forum'; // API 호출 함수 가져오기
  
  export default {
    setup() {
      const route = useRoute();
      const router = useRouter();
      const post = ref({});
      const currentUserId = 1;  // 로그인된 사용자의 ID (임시 값, 실제로는 인증된 사용자의 ID를 넣어야 함)
  
      const fetchPost = (postId) => {
        getPostDetail(postId, 
          (response) => {
            post.value = response.data;
            console.log('게시글 상세 정보:', post.value);
          },
          (error) => {
            console.error('게시글 상세 정보를 가져오는 중 에러 발생:', error);
          }
        );
      };
  
      // 라우트에서 postId 가져오기
      const postId = route.params.postId;
  
      onMounted(() => {
        if (postId) {
          fetchPost(postId); // 전달받은 postId를 이용해 게시글 데이터를 가져옴
        } else {
          console.error('postId가 정의되지 않았습니다.');
        }
      });
  
      const editPost = () => {
        router.push(`/EditPost/${post.value.id}`);  // 수정 페이지로 이동
      };
  
      const deletePost = () => {
        // deletePost 함수 구현 필요
        console.log('게시글 삭제 로직을 구현하세요');
      };
  
      const fnBackToList = () => {
        router.push('/PostList');  // 목록 페이지로 이동
      };
  
      const formatDate = (dateString) => {
        const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
        return new Date(dateString).toLocaleDateString(undefined, options);
      };
  
      return {
        post,
        currentUserId,
        editPost,
        deletePost,
        fnBackToList,
        formatDate,
      };
    },
  };
  </script>
  
  <style scoped>
  .post-detail {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
  }
  
  button {
    margin-right: 10px;
  }
  
  .reply-section {
    margin-top: 30px;
  }
  
  .reply-section h3 {
    margin-bottom: 10px;
  }
  
  .common-buttons {
    margin-top: 20px;
  }
  </style>
  