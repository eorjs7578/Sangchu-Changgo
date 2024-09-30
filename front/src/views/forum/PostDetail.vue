<template>
    <div class="post-detail">
        <h1>{{ post.title }}</h1>
        <p><strong>작성자:</strong> {{ post.member_id }}</p>
        <p><strong>내용:</strong> {{ post.content }}</p>
        <p><strong>작성일:</strong> {{ formatDate(post.created_at) }}</p>
        <p><strong>조회수:</strong> {{ post.view }}</p>

        <!-- 게시글 수정/삭제 버튼 (작성자만 가능) -->
        <div v-if="post.member_id === currentUserId">
            <button @click="handleEditPost">수정</button>
            <button @click="handleDeletePost">삭제</button>
        </div>

        <!-- 게시글 작성자와 로그인된 사용자의 ID를 콘솔에 출력 -->
        <div v-else>
            <p>현재 로그인된 사용자 ID: {{ currentUserId }}</p>
            <p>게시글 작성자 ID: {{ post.member_id }}</p>
        </div>

        <!-- 댓글 목록 -->
        <div class="reply-section">
            <h3>댓글</h3>
            <ul>
                <li v-for="(reply, index) in post.reply" :key="reply.reply_id">
                    <p><strong>작성자:</strong> {{ reply.member_id }}</p>
                    <p>{{ reply.content }}</p>
                    <p><strong>작성일:</strong> {{ formatDate(reply.created_at) }}</p>
                    <!-- 댓글 수정/삭제 버튼 (댓글 작성자가 currentUserId와 동일할 때만) -->
                    <div v-if="reply.member_id === currentUserId">
                        <button @click="handleEditReply(reply.reply_id, reply.content)">
                            수정
                        </button>
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

        <!-- 목록으로 돌아가기 버튼 -->
        <div class="common-buttons">
            <button @click="navigateBackToList">목록으로 돌아가기</button>
        </div>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAccountStore } from '@/stores/useAccountStore'; // Pinia store import
import { getPostDetail, createReply, deleteReply, updateReply, deletePost } from '@/api/forum';

export default {
    setup() {
        const route = useRoute();
        const router = useRouter();
        const post = ref({});
        const newReply = ref('');

        // Pinia 스토어에서 로그인한 사용자 정보 불러오기
        const accountStore = useAccountStore();

        // currentUserId를 전역적으로 사용할 수 있게 선언
        const currentUserId = ref(null);

        // 사용자 인증 상태 체크 및 currentUserId 설정
        onMounted(() => {
            accountStore.checkAuthStatus().then(() => {
                currentUserId.value = accountStore.userInfo?.id;
                console.log('로그인된 사용자 ID:', currentUserId.value);
            });
        });

        // 게시글 상세 정보 가져오기
        const fetchPostDetail = (postId) => {
            getPostDetail(
                postId,
                (response) => {
                    post.value = response.data;
                    console.log('게시글 상세 정보:', post.value);
                    console.log('게시글 작성자 ID:', post.value.member_id);
                },
                (error) => {
                    console.error('게시글 상세 정보를 가져오는 중 에러 발생:', error);
                }
            );
        };

        // 라우트에서 postId와 boardId 가져오기
        const postId = route.params.postId;
        const boardId = route.params.boardId; // 목록으로 돌아갈 때 사용할 boardId

        onMounted(() => {
            if (postId) {
                fetchPostDetail(postId);
            } else {
                console.error('postId가 정의되지 않았습니다.');
            }
        });

        // PostDetail.vue에서 게시글 수정 버튼
        const handleEditPost = () => {
            router.push({
                name: 'EditPost',
                params: { postId, boardId },
            });
        };

        // 게시글 삭제 함수
        const handleDeletePost = () => {
            deletePost(
                postId,
                (response) => {
                    console.log('게시글 삭제 성공');
                    // 삭제 후 해당 boardId의 목록으로 이동 (패스베리어블 사용)
                    router.push({
                        name: 'PostList',
                        params: { boardId }, // 패스 변수로 boardId 전달
                    });
                },
                (error) => {
                    console.error('게시글 삭제 중 에러 발생:', error);
                }
            );
        };

        // 댓글 등록 함수
        const handleSubmitReply = () => {
            if (!newReply.value) {
                alert('댓글 내용을 입력하세요.');
                return;
            }

            if (!currentUserId.value) {
                console.error('로그인된 사용자 ID를 찾을 수 없습니다.');
                return;
            }

            const payload = { content: newReply.value };
            createReply(
                postId,
                payload,
                (response) => {
                    console.log('댓글 등록 성공:', response);
                    post.value.reply.push({
                        reply_id: response.data.reply_id,
                        member_id: currentUserId.value, // 로그인된 사용자의 ID 사용
                        content: newReply.value,
                        created_at: new Date().toISOString(),
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
            if (!replyId) {
                console.error('삭제할 댓글의 ID가 정의되지 않았습니다.');
                return;
            }

            deleteReply(
                postId,
                replyId,
                (response) => {
                    console.log('댓글 삭제 성공:', response);
                    post.value.reply = post.value.reply.filter(
                        (reply) => reply.reply_id !== replyId
                    );
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
                updateReply(
                    postId,
                    replyId,
                    payload,
                    (response) => {
                        console.log('댓글 수정 성공:', response);
                        const reply = post.value.reply.find((reply) => reply.reply_id === replyId);
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

        // 목록으로 돌아가기 함수 (boardId를 패스변수로 전달)
        const navigateBackToList = () => {
            console.log('boardId:', boardId);

            if (boardId) {
                router.push(`/PostList/${boardId}`); // 패스 변수로 boardId 전달
            } else {
                console.error('boardId가 정의되지 않았습니다.');
            }
        };

        const formatDate = (dateString) => {
            const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
            return new Date(dateString).toLocaleDateString(undefined, options);
        };

        return {
            post,
            newReply,
            currentUserId,
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
