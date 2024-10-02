<template>
    <div class="custom-container">
        <div class="custom-card">
            <h2 class="custom-card-title text-center mb-4">게시판</h2>

            <div class="custom-button-group">
                <button type="button" class="custom-btn" @click="handleCreatePost">
                    새 글 등록
                </button>
            </div>

            <div class="table-responsive">
                <table class="custom-table">
                    <thead>
                        <tr>
                            <th>No</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>등록일시</th>
                            <th>조회수</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(row, idx) in postList" :key="row.id">
                            <td>{{ idx + 1 + currentPage * pageSize }}</td>
                            <td>
                                <a
                                    href="#"
                                    @click.prevent="viewPost(row.id)"
                                    class="custom-link"
                                    >{{ row.title }}</a
                                >
                            </td>
                            <td>{{ row.member_id }}</td>
                            <td>{{ formatDate(row.created_at) }}</td>
                            <td>{{ row.view }}</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <nav aria-label="Page navigation">
                <ul class="custom-pagination">
                    <li class="custom-page-item" :class="{ disabled: currentPage === 0 }">
                        <a class="custom-page-link" href="#" @click.prevent="loadPreviousPage">
                            이전
                        </a>
                    </li>
                    <li class="custom-page-item active">
                        <span class="custom-page-link">
                            {{ currentPage + 1 }} / {{ totalPages }}
                        </span>
                    </li>
                    <li class="custom-page-item" :class="{ disabled: isLastPage }">
                        <a class="custom-page-link" href="#" @click.prevent="loadNextPage">
                            다음
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</template>


<script>
import { getPostListByBoard } from '@/api/forum'; // API 호출 함수 가져오기
import { useRoute, useRouter } from 'vue-router';

export default {
    data() {
        return {
            postList: [], // 서버로부터 받아온 게시글 목록
            totalElements: 0, // 전체 게시글 수
            totalPages: 0, // 전체 페이지 수
            currentPage: 0, // 현재 페이지
            pageSize: 0, // 한 페이지당 게시글 수
            isFirstPage: true, // 첫 번째 페이지 여부
            isLastPage: false, // 마지막 페이지 여부
        };
    },
    mounted() {
        const route = useRoute();

        this.boardId = route.params.boardId;
        console.log('넘겨받은 boardId: ', this.boardId);

        // URL에서 page 값이 있다면 해당 페이지로 로드, 없으면 첫 페이지 로드
        const page = route.query.page ? parseInt(route.query.page) : 0;
        this.loadPosts(page); // 첫 페이지 로딩
    },
    setup() {
        const route = useRoute(); // 현재 경로 정보 가져오기
        const router = useRouter(); // router 객체 가져오기

        const handleCreatePost = () => {
            // 현재 boardId 가져오기
            const boardId = route.params.boardId; // boardId가 없으면 기본값 80 사용
            router.push({
                path: '/CreatePost',
                query: { boardId }, // boardId를 쿼리로 전달
            });
        };

        return {
            handleCreatePost,
        };
    },
    methods: {
        // 게시글 목록 불러오기 (페이지네이션 적용)
        loadPosts(page) {
            // 페이지 범위를 벗어나지 않도록 제한
            if (this.totalPages !== 0 && (page < 0 || page >= this.totalPages)) return;

            this.currentPage = page; // 페이지 값 업데이트
            getPostListByBoard(
                this.boardId,
                this.currentPage,
                this.pageSize,
                (response) => {
                    console.log('게시글 목록 불러오기 성공:', response.data);

                    // API로부터 받아온 데이터로 상태 업데이트
                    this.postList = response.data.posts;
                    this.totalElements = response.data.total_elements;
                    this.totalPages = response.data.total_pages;
                    this.pageSize = response.data.page_size;
                    this.isFirstPage = response.data.first_page;
                    this.isLastPage = response.data.last_page;

                    // 페이지 상태를 URL에 저장
                    this.$router.push({ query: { page: this.currentPage } });
                },
                (error) => {
                    console.error('게시글 목록 불러오기 중 에러 발생:', error);
                }
            );
        },
        // 이전 페이지 로딩
        loadPreviousPage() {
            console.log('이전페이지 이동');
            if (this.currentPage > 0) {
                // 첫 페이지가 아닌 경우에만 실행
                this.loadPosts(this.currentPage - 1); // 이전 페이지로 이동
            }
        },
        // 다음 페이지 로딩
        loadNextPage() {
            console.log('다음페이지 이동');
            if (!this.isLastPage) {
                // 마지막 페이지가 아닌 경우에만 실행
                this.loadPosts(this.currentPage + 1); // 다음 페이지로 이동
            }
        },
        // 게시글 상세 페이지로 이동
        viewPost(postId) {
            // PostDetail로 postId와 boardId를 패스 변수로 넘겨줌
            this.$router.push({ name: 'PostDetail', params: { postId, boardId: this.boardId } });
        },
        formatDate(dateString) {
            const date = new Date(dateString);

            // 날짜 부분 (연도-월-일)
            const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
            const formattedDate = date.toLocaleDateString(undefined, options);

            // 시간 부분 (시:분)
            const hours = date.getHours().toString().padStart(2, '0'); // 시
            const minutes = date.getMinutes().toString().padStart(2, '0'); // 분

            // 날짜와 시간 결합
            return `${formattedDate} ${hours}:${minutes}`;
        },
    },
};
</script>

<style scoped>
/* 기본 컨테이너 설정 */
.custom-container {
    max-width: 900px;
    margin: 50px auto;
    padding: 20px;
    background-color: #f8f9fa;
    border-radius: 15px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

/* 카드 스타일 */
.custom-card {
    background-color: #ffffff;
    padding: 30px;
    border-radius: 15px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

/* 제목 스타일 */
.custom-card-title {
    font-size: 1.8rem;
    color: #2c3e50;
    margin-bottom: 20px;
}

/* 버튼 그룹 */
.custom-button-group {
    text-align: right;
    margin-bottom: 20px;
}

.custom-btn {
    background-color: #3498db;
    color: white;
    padding: 10px 20px;
    border-radius: 8px;
    border: none;
    cursor: pointer;
    transition: background-color 0.3s, transform 0.2s;
}

.custom-btn:hover {
    background-color: #2980b9;
    transform: translateY(-2px);
}

.custom-btn:active {
    transform: translateY(0);
}

/* 테이블 스타일 */
.custom-table {
    width: 100%;
    border-collapse: collapse;
    font-size: 0.9rem;
}

.custom-table th, .custom-table td {
    padding: 15px;
    text-align: left;
    border-bottom: 1px solid #ddd;
}

.custom-table thead th {
    background-color: #f4f6f9;
    color: #2c3e50;
}

.custom-table tbody tr:hover {
    background-color: #f9f9f9;
}

.custom-link {
    color: #3498db;
    text-decoration: none;
}

.custom-link:hover {
    text-decoration: underline;
}

/* 페이지네이션 */
.custom-pagination {
    list-style: none;
    padding: 0;
    display: flex;
    justify-content: center;
    margin-top: 20px;
}

.custom-page-item {
    margin: 0 5px;
}

.custom-page-link {
    display: inline-block;
    padding: 10px 15px;
    border-radius: 8px;
    background-color: #ecf0f1;
    color: #2c3e50;
    text-decoration: none;
    transition: background-color 0.3s;
}

.custom-page-link:hover {
    background-color: #bdc3c7;
}

.custom-page-item.disabled .custom-page-link {
    background-color: #e5e7e9;
    color: #95a5a6;
    pointer-events: none;
}
</style>
