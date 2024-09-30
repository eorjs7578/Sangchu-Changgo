<template>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-8">
                <div class="card">
                    <div class="card-body">
                        <h2 class="card-title text-center mb-4">
                            {{ isEdit ? '게시글 수정' : '새 게시글 작성' }}
                        </h2>
                        <form @submit.prevent="submitPost">
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
                                <button type="submit" class="btn btn-primary">
                                    {{ isEdit ? '수정 완료' : '작성 완료' }}
                                </button>
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
import { createPost, getPostDetail, updatePost } from '@/api/forum'; // API 함수 가져오기

export default {
    setup() {
        const route = useRoute();
        const router = useRouter();

        // 경로에서 boardId와 postId를 가져옴
        const boardId = route.query.boardId; // 쿼리에서 boardId 가져오기 (없으면 기본값)
        const postId = route.params.postId; // postId가 있으면 수정, 없으면 새 글 작성
        const isEdit = !!postId; // postId가 있으면 true, 없으면 false로 구분

        const title = ref('');
        const content = ref('');

        // 게시글 수정 시 기존 데이터 가져오기
        const fetchPost = () => {
            if (isEdit) {
                getPostDetail(
                    postId,
                    (response) => {
                        title.value = response.data.title;
                        content.value = response.data.content;
                    },
                    (error) => {
                        console.error('게시글을 불러오는 중 에러 발생:', error);
                    }
                );
            }
        };

        onMounted(fetchPost); // 페이지 로드 시 데이터 가져오기

        const submitPost = () => {
            console.log('submitPost시 board_id: ', boardId);
            const payload = {
                board_id: boardId, // 경로에서 가져온 boardId 사용
                title: title.value, // 제목
                content: content.value, // 내용
            };

            console.log('보내는 데이터:', payload); // 확인을 위한 로그

            if (isEdit) {
                // 게시글 수정 처리
                updatePost(
                    postId,
                    payload,
                    (response) => {
                        console.log('게시글 수정 성공:', response);
                        router.push(`/PostDetail/${postId}`); // 수정 후 게시글 상세 페이지로 이동
                    },
                    (error) => {
                        console.error('게시글 수정 중 에러 발생:', error);
                    }
                );
            } else {
                // 새 게시글 작성 처리
                createPost(
                    payload,
                    (response) => {
                        console.log('게시글 작성 성공:', response);
                        router.push(`/PostList/${boardId}`); // 작성 후 해당 게시판의 게시글 목록으로 이동
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
            submitPost,
            isEdit,
        };
    },
};
</script>
