<template>
    <div class="custom-container">
        <div class="custom-post">
            <h1 class="custom-post-title">{{ post.title }}</h1>
            <div class="custom-post-meta">
                <span><strong>작성자:</strong> {{ post.member_id }}</span>
                <div>
                    <span><strong>작성일:</strong> {{ formatDate(post.created_at) }}</span>
                    <span class="custom-post-view"><strong> 조회수:</strong> {{ post.view }}</span> 
                </div>
            </div>
            <div>
                
                <p class="custom-post-content">{{ post.content }}</p>
            </div>

            <div v-if="post.member_id === currentUserId" class="custom-post-actions">
                <button @click="handleEditPost" class="custom-btn primary">수정</button>
                <button @click="handleDeletePost" class="custom-btn danger">삭제</button>
            </div>
        </div>

        <div class="custom-comments">
            <h3 class="custom-section-title">댓글</h3>
            <ul class="custom-reply-list">
                <li
                    v-for="(reply, index) in post.reply"
                    :key="reply.reply_id"
                    class="custom-reply-item"
                >
                    <div class="custom-reply-meta">
                        <span><strong>작성자:</strong> {{ reply.member_id }}</span>
                        <span><small>{{ formatDate(reply.created_at) }}</small></span>
                    </div>
                    <p class="custom-reply-content">{{ reply.content }}</p>
                    <div v-if="reply.member_id === currentUserId" class="custom-reply-actions">
                        <button @click="handleEditReply(reply.reply_id, reply.content)" class="custom-btn outline-primary">
                            수정
                        </button>
                        <button @click="handleDeleteReply(reply.reply_id)" class="custom-btn outline-danger">
                            삭제
                        </button>
                    </div>
                </li>
            </ul>
        </div>

        <div v-if="currentUserId" class="custom-add-comment">
            <h3 class="custom-section-title">댓글 작성</h3>
            <textarea
                v-model="newReply"
                class="custom-textarea"
                rows="3"
                placeholder="댓글을 입력하세요"
            ></textarea>
            <button @click="handleSubmitReply" class="custom-btn primary">댓글 등록하기</button>
        </div>

        <div class="custom-back-button">
            <button @click="navigateBackToList" class="custom-btn secondary">목록으로 돌아가기</button>
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
            const date = new Date(dateString);

            // 날짜 부분 (연도-월-일)
            const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
            const formattedDate = date.toLocaleDateString(undefined, options);

            // 시간 부분 (시:분)
            const hours = date.getHours().toString().padStart(2, '0'); // 시
            const minutes = date.getMinutes().toString().padStart(2, '0'); // 분

            // 날짜와 시간 결합
            return `${formattedDate} ${hours}:${minutes}`;
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
/* 기본 컨테이너 설정 */
.custom-container {
    max-width: 800px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

/* 게시글 스타일 */
.custom-post {
    margin-bottom: 40px;
}

.custom-post-title {
    font-size: 2rem;
    color: #333;
    margin-bottom: 10px;
}

.custom-post-meta {
    display: flex;
    justify-content: space-between;
    font-size: 0.9rem;
    color: #555;
    margin-bottom: 20px;
}

.custom-post-content {
    font-size: 1.1rem;
    line-height: 1.6;
    color: #444;
    margin-bottom: 10%;
    margin-top: 10%;
}

.custom-post-view {
    font-size: 0.9rem;
    color: #777;
}

.custom-post-actions {
    display: flex;
    gap: 10px;
    justify-content: flex-end
}

.custom-post-info {
    margin-top: 20px;
}

.info-text {
    color: #999;
    font-size: 0.9rem;
}

/* 댓글 목록 스타일 */
.custom-comments {
    margin-bottom: 40px;
}

.custom-section-title {
    font-size: 1.5rem;
    margin-bottom: 15px;
    color: #333;
}

.custom-reply-list {
    list-style: none;
    padding: 0;
}

.custom-reply-item {
    padding: 15px;
    border-bottom: 1px solid #e0e0e0;
}

.custom-reply-meta {
    display: flex;
    justify-content: space-between;
    font-size: 0.9rem;
    color: #666;
}

.custom-reply-content {
    margin-top: 10px;
    font-size: 1rem;
    color: #333;
}

.custom-reply-actions {
    margin-top: 10px;
    display: flex;
    gap: 10px;
    justify-content: flex-end
}

/* 댓글 작성 섹션 */
.custom-add-comment {
    margin-bottom: 40px;
}

.custom-textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    font-size: 1rem;
    color: #333;
    margin-bottom: 15px;
}

/* 버튼 스타일 */
.custom-btn {
    padding: 5px 7px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 13px;
    transition: background-color 0.3s ease;
}

.custom-btn.primary {
    background-color: #3498db;
    color: white;
}

.custom-btn.primary:hover {
    background-color: #2980b9;
}

.custom-btn.danger {
    background-color: #e74c3c;
    color: white;
}

.custom-btn.danger:hover {
    background-color: #c0392b;
}

.custom-btn.secondary {
    background-color: #7f8c8d;
    color: white;
}

.custom-btn.secondary:hover {
    background-color: #95a5a6;
}

.custom-btn.outline-primary {
    background-color: transparent;
    border: 2px solid #3498db;
    color: #3498db;
}

.custom-btn.outline-primary:hover {
    background-color: #3498db;
    color: white;
}

.custom-btn.outline-danger {
    background-color: transparent;
    border: 2px solid #e74c3c;
    color: #e74c3c;
}

.custom-btn.outline-danger:hover {
    background-color: #e74c3c;
    color: white;
}

/* 목록으로 돌아가기 버튼 */
.custom-back-button {
    text-align: center;
    margin-top: 20px;
}
</style>
