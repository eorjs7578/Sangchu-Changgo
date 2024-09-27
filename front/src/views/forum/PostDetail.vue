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
        <li v-for="(reply, index) in post.reply" :key="reply.reply_id">
          <p><strong>작성자:</strong> {{ reply.member_id }}</p>
          <p>{{ reply.content }}</p>
          <p><strong>작성일:</strong> {{ formatDate(reply.created_at) }}</p>
          <!-- 댓글 수정/삭제 버튼 -->
          <div>
            <button @click="handleEditReply(reply.reply_id, reply.content)">수정</button>
            <button @click="handleDeleteReply(reply.reply_id)">삭제</button>
          </div>
        </li>
      </ul>
    </div>

    <!-- 댓글 작성 -->
    <div class="reply-form">
      <textarea v-model="newReply" placeholder="댓글을 입력하세요"></textarea>
      <button @click="handleSubmitReply">댓글 등록하기</button>
    </div>

    <!-- 게시글 수정/삭제 버튼 -->
    <div v-if="post.member_id === currentUserId">
      <button @click="handleEditPost">수정</button>
      <button @click="handleDeletePost">삭제</button>
    </div>

    <!-- 목록으로 돌아가기 버튼 -->
    <div class="common-buttons">
      <button @click="navigateBackToList">목록으로 돌아가기</button>
    </div>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import {
  getPostDetail,
  createReply,
  deleteReply,
  updateReply,
  deletePost,
} from '@/api/forum';

export default {
  setup() {
    const route = useRoute();
    const router = useRouter();
    const post = ref({});
    const currentUserId = 1; // 로그인된 사용자의 ID (임시 값)
    const newReply = ref(''); // 새 댓글 내용

    // 게시글 상세 정보 가져오기
    const fetchPostDetail = (postId) => {
      getPostDetail(postId, 
        (response) => {
          post.value = response.data;
          console.log('게시글 상세 정보:', post.value);
          console.log('댓글 목록:', post.value.reply);
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
        fetchPostDetail(postId);
      } else {
        console.error('postId가 정의되지 않았습니다.');
      }
    });

    // 게시글 수정 함수
    const handleEditPost = () => {
      router.push(`/CreatePost/${post.value.id}`);
    };

    // 게시글 삭제 함수
    const handleDeletePost = () => {
      deletePost(postId,
        (response) => {
          console.log('게시글 삭제 성공');
          router.push('/PostList');
        },
        (error) => {
          console.error('게시글 삭제 중 에러 발생:', error);
        }
      );
    };

    // 댓글 등록 함수
    const handleSubmitReply = () => {
      if (!newReply.value) {
        alert("댓글 내용을 입력하세요.");
        return;
      }
      const payload = { content: newReply.value };
      createReply(postId, payload, 
        (response) => {
          console.log('댓글 등록 성공:', response);
          post.value.reply.push({
            reply_id: response.data.reply_id,
            member_id: currentUserId,
            content: newReply.value,
            created_at: new Date().toISOString()
          });
          newReply.value = ''; // 댓글 작성 필드 초기화
        },
        (error) => {
          console.error('댓글 등록 중 에러 발생:', error);
        }
      );
    };

    // 댓글 삭제 함수
    const handleDeleteReply = (replyId) => {
      console.log('삭제할 댓글 ID:', replyId); // 댓글 ID 확인
      if (!replyId) {
        console.error('삭제할 댓글의 ID가 정의되지 않았습니다.');
        return;
      }

      deleteReply(postId, replyId,
        (response) => {
          console.log('댓글 삭제 성공:', response);
          post.value.reply = post.value.reply.filter(reply => reply.reply_id !== replyId);
        },
        (error) => {
          console.error('댓글 삭제 중 에러 발생:', error);
        }
      );
    };

    // 댓글 수정 함수
    const handleEditReply = (replyId, currentContent) => {
      const newContent = prompt('댓글 내용을 수정하세요:', currentContent);
      if (newContent && newContent !== currentContent) {
        const payload = { content: newContent };
        updateReply(postId, replyId, payload,
          (response) => {
            console.log('댓글 수정 성공:', response);
            const reply = post.value.reply.find(reply => reply.reply_id === replyId);
            if (reply) {
              reply.content = newContent;
            }
          },
          (error) => {
            console.error('댓글 수정 중 에러 발생:', error);
          }
        );
      }
    };

    // 목록으로 돌아가기 함수
    const navigateBackToList = () => {
      router.push('/PostList');
    };

    const formatDate = (dateString) => {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    };

    return {
      post,
      currentUserId,
      newReply,
      handleEditPost,
      handleDeletePost,
      handleSubmitReply,
      handleDeleteReply,
      handleEditReply,
      navigateBackToList,
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

.reply-form {
  margin-top: 20px;
}

.reply-form textarea {
  width: 100%;
  height: 60px;
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}
</style>
