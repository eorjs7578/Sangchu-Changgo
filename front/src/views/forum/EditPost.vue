<template>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h2 class="card-title text-center mb-4">게시글 수정</h2>
                        <form @submit.prevent="submitEdit">
                            <div class="mb-3">
                                <label for="title" class="form-label">제목</label>
                                <input
                                    id="title"
                                    v-model="title"
                                    type="text"
                                    class="form-control"
                                    required
                                    placeholder="제목을 입력하세요"
                                />
                            </div>
                            <div class="mb-3">
                                <label for="content" class="form-label">내용</label>
                                <textarea
                                    id="content"
                                    v-model="content"
                                    rows="6"
                                    class="form-control"
                                    required
                                    placeholder="내용을 입력하세요"
                                ></textarea>
                            </div>
                            <div class="text-end">
                                <button type="submit" class="btn btn-primary">수정 완료</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getPostDetail, updatePost } from '@/api/forum'; // API 함수 가져오기

export default {
    setup() {
        const route = useRoute();
        const router = useRouter();

        const postId = route.params.postId; // postId를 라우트에서 가져옴
        const boardId = route.params.boardId; // boardId도 라우트에서 가져오기

        const title = ref(''); // 제목
        const content = ref(''); // 내용

        // 기존 게시글 데이터를 가져오는 함수
        const fetchPostData = () => {
            getPostDetail(
                postId,
                (response) => {
                    title.value = response.data.title; // 제목 채우기
                    content.value = response.data.content; // 내용 채우기
                },
                (error) => {
                    console.error('게시글 데이터를 불러오는 중 에러 발생:', error);
                }
            );
        };

        // 페이지 로딩 시 게시글 데이터를 가져옴
        onMounted(fetchPostData);

        // 목록으로 돌아가는 함수
        const navigateBackToList = () => {
            console.log('boardId:', boardId);
            if (boardId) {
                router.push(`/PostList/${boardId}`); // 수정 완료 후 목록으로 이동
            } else {
                console.error('boardId가 정의되지 않았습니다.');
            }
        };

        // 수정 제출 함수
        const submitEdit = () => {
            const payload = {
                board_id: boardId, // 게시판 ID
                title: title.value, // 수정된 제목
                content: content.value, // 수정된 내용
            };

            // 게시글 수정 API 호출
            updatePost(
                postId,
                payload,
                (response) => {
                    console.log('게시글 수정 성공:', response);
                    // 수정 완료 후 목록으로 이동
                    navigateBackToList();
                },
                (error) => {
                    console.error('게시글 수정 중 에러 발생:', error);
                }
            );
        };

        return {
            title,
            content,
            submitEdit,
        };
    },
};
</script>
