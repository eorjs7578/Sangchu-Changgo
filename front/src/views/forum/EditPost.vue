<template>
    <div class="edit-post">
        <h1>게시글 수정</h1>

        <!-- 제목 입력 -->
        <div class="mb-4">
            <label for="title">제목 </label>
            <input
                id="title"
                v-model="title"
                type="text"
                required
                class="input"
                placeholder="제목을 입력하세요"
            />
        </div>

        <!-- 내용 입력 -->
        <div class="mb-4">
            <label for="content">내용</label>
            <textarea
                id="content"
                v-model="content"
                rows="6"
                required
                class="input"
                placeholder="내용을 입력하세요"
            ></textarea>
        </div>

        <!-- 제출 버튼 -->
        <div class="flex justify-end">
            <button @click="submitEdit" class="button">수정 완료</button>
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

<style scoped>
.edit-post {
    max-width: 800px;
    margin: 0 auto;
    padding: 20px;
}
.input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    margin-bottom: 10px;
}
.button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}
</style>
