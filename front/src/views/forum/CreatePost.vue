<template>
    <div class="custom-container">
        <div class="custom-card">
            <h2 class="custom-card-title">
                {{ isEdit ? '게시글 수정' : '새 게시글 작성' }}
            </h2>
            <form @submit.prevent="submitPost" class="custom-form">
                <div class="custom-form-group">
                    <label for="title" class="custom-form-label">제목</label>
                    <input
                        id="title"
                        v-model="title"
                        type="text"
                        class="custom-form-control"
                        required
                        placeholder="제목을 입력하세요"
                    />
                </div>
                <div class="custom-form-group">
                    <label for="content" class="custom-form-label">내용</label>
                    <textarea
                        id="content"
                        v-model="content"
                        rows="6"
                        class="custom-form-control"
                        required
                        placeholder="내용을 입력하세요"
                    ></textarea>
                </div>
                <div class="custom-form-submit">
                    <button type="submit" class="custom-btn">
                        {{ isEdit ? '수정 완료' : '작성 완료' }}
                    </button>
                </div>
            </form>
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


<style scoped>
.custom-container {
    max-width: 600px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f0f4f8;
    border-radius: 12px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.custom-card {
    background-color: #ffffff;
    border-radius: 12px;
    padding: 30px;
}

.custom-card-title {
    text-align: center;
    margin-bottom: 1.5rem;
    font-size: 1.8rem;
    color: #2c3e50;
}

.custom-form {
    display: flex;
    flex-direction: column;
}

.custom-form-group {
    margin-bottom: 1.5rem;
    display: flex;
    align-items: center
}

.custom-form-label {
    font-weight: 600;
    color: #34495e;
    margin-bottom: 0.5rem;
    display: inline;
}

.custom-form-control {
    border: 0;
    border-radius: 6px;
    padding: 12px;
    font-size: 1rem;
    transition: border-color 0.3s, box-shadow 0.3s;
    margin: 1vh 2vw;
    display: inline-block;
    width: 80%;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.custom-form-control:focus {
    border-color: #3498db;
    box-shadow: 0 0 8px rgba(52, 152, 219, 0.5);
    outline: none;
}

.custom-form-submit {
    text-align: right;
}

.custom-btn {
    background-color: #3498db;
    color: white;
    border: none;
    padding: 12px 20px;
    border-radius: 6px;
    cursor: pointer;
    font-size: 1rem;
    transition: background-color 0.3s, transform 0.2s;
}

.custom-btn:hover {
    background-color: #2980b9;
    transform: translateY(-2px);
}

.custom-btn:active {
    transform: translateY(0);
}
</style>