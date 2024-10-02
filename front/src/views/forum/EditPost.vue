<template>
    <div class="custom-container">
        <div class="custom-form-card">
            <h2 class="custom-form-title">게시글 수정</h2>
            <form @submit.prevent="submitEdit" class="custom-form">
                <div class="custom-form-group">
                    <label for="title" class="custom-label">제목</label>
                    <input
                        id="title"
                        v-model="title"
                        type="text"
                        class="custom-input"
                        required
                        placeholder="제목을 입력하세요"
                    />
                </div>
                <div class="custom-form-group">
                    <label for="content" class="custom-label">내용</label>
                    <textarea
                        id="content"
                        v-model="content"
                        rows="6"
                        class="custom-textarea"
                        required
                        placeholder="내용을 입력하세요"
                    ></textarea>
                </div>
                <div class="custom-form-actions">
                    <button type="submit" class="custom-btn primary">수정 완료</button>
                </div>
            </form>
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
/* 기본 컨테이너 */
.custom-container {
    max-width: 600px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 10px;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

/* 폼 카드 스타일 */
.custom-form-card {
    padding: 30px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.05);
}

/* 폼 타이틀 */
.custom-form-title {
    font-size: 1.8rem;
    color: #333;
    margin-bottom: 20px;
    text-align: center;
}

/* 폼 그룹 */
.custom-form-group {
    margin-bottom: 20px;
}

/* 레이블 스타일 */
.custom-label {
    font-size: 1rem;
    color: #555;
    margin-bottom: 10px;
    display: block;
}

/* 입력 필드 스타일 */
.custom-input {
    width: 100%;
    padding: 12px;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
    transition: border-color 0.3s ease;
}

.custom-input:focus {
    border-color: #3498db;
    outline: none;
}

/* 텍스트 영역 스타일 */
.custom-textarea {
    width: 100%;
    padding: 12px;
    font-size: 1rem;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1);
    transition: border-color 0.3s ease;
}

.custom-textarea:focus {
    border-color: #3498db;
    outline: none;
}

/* 버튼 섹션 */
.custom-form-actions {
    text-align: right;
}

/* 버튼 스타일 */
.custom-btn {
    padding: 10px 20px;
    font-size: 1rem;
    border-radius: 5px;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.custom-btn.primary {
    background-color: #3498db;
    color: white;
}

.custom-btn.primary:hover {
    background-color: #2980b9;
}
</style>
