<template>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card mb-4">
                    <div class="card-body">
                        <h1 class="card-title">{{ post.title }}</h1>
                        <div class="d-flex justify-content-between mb-3">
                            <span><strong>작성자:</strong> {{ post.member_id }}</span>
                            <span><strong>작성일:</strong> {{ formatDate(post.created_at) }}</span>
                        </div>
                        <p class="card-text"><strong>내용:</strong> {{ post.content }}</p>
                        <p class="text-muted"><strong>조회수:</strong> {{ post.view }}</p>

                        <div v-if="post.member_id === currentUserId" class="mt-3">
                            <button @click="handleEditPost" class="btn btn-primary me-2">
                                수정
                            </button>
                            <button @click="handleDeletePost" class="btn btn-danger">삭제</button>
                        </div>
                        <div v-else class="mt-3">
                            <p class="mb-0">
                                <small>현재 로그인된 사용자 ID: {{ currentUserId }}</small>
                            </p>
                            <p>
                                <small>게시글 작성자 ID: {{ post.member_id }}</small>
                            </p>
                        </div>
                    </div>
                </div>

                <div class="card mb-4">
                    <div class="card-body">
                        <h3 class="card-title">댓글</h3>
                        <ul class="list-unstyled">
                            <li
                                v-for="(reply, index) in post.reply"
                                :key="reply.reply_id"
                                class="mb-3 pb-3 border-bottom"
                            >
                                <div class="d-flex justify-content-between">
                                    <span><strong>작성자:</strong> {{ reply.member_id }}</span>
                                    <span
                                        ><small>{{ formatDate(reply.created_at) }}</small></span
                                    >
                                </div>
                                <p class="mt-2">{{ reply.content }}</p>
                                <div v-if="reply.member_id === currentUserId" class="mt-2">
                                    <button
                                        @click="handleEditReply(reply.reply_id, reply.content)"
                                        class="btn btn-sm btn-outline-primary me-2"
                                    >
                                        수정
                                    </button>
                                    <button
                                        @click="handleDeleteReply(reply.reply_id)"
                                        class="btn btn-sm btn-outline-danger"
                                    >
                                        삭제
                                    </button>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="card mb-4">
                    <div class="card-body">
                        <h3 class="card-title">댓글 작성</h3>
                        <textarea
                            v-model="newReply"
                            class="form-control mb-3"
                            rows="3"
                            placeholder="댓글을 입력하세요"
                        ></textarea>
                        <button @click="handleSubmitReply" class="btn btn-primary">
                            댓글 등록하기
                        </button>
                    </div>
                </div>

                <div class="text-center">
                    <button @click="navigateBackToList" class="btn btn-secondary">
                        목록으로 돌아가기
                    </button>
                </div>
            </div>
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
