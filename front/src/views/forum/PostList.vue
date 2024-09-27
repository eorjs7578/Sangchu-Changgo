<template>
  <div class="board-list">
    <div class="common-buttons">
      <!-- 등록 버튼 -->
      <button type="button" class="w3-button w3-round w3-blue-gray" @click="handleCreatePost">등록</button>
    </div>

    <!-- 게시판 목록 테이블 -->
    <table class="w3-table-all">
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
          <td>{{ idx + 1 + (currentPage * pageSize) }}</td>
          <td><a @click="viewPost(row.id)">{{ row.title }}</a></td>
          <td>{{ row.member_id }}</td>
          <td>{{ formatDate(row.created_at) }}</td>
          <td>{{ row.view }}</td>
        </tr>
      </tbody>
    </table>

    <!-- 페이지네이션 버튼 -->
    <div class="pagination">
      <!-- 첫 번째 페이지면 '이전' 버튼 비활성화 -->
      <button  @click="loadPreviousPage">이전</button>
      <span>{{ currentPage + 1 }} / {{ totalPages }}</span>
      <!-- 마지막 페이지면 '다음' 버튼 비활성화 -->
      <button :disabled="isLastPage" @click="loadNextPage">다음</button>
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

    this.boardId = route.params.boardId || 80; // 기본 게시판 ID
    this.boardId = 80;
    
    // TODO: 
    // let postId = route.params.postId;// :id는 동적 라우트 매개변수
    // console.log(postId)
    
    // URL에서 page 값이 있다면 해당 페이지로 로드, 없으면 첫 페이지 로드
    const page = route.query.page ? parseInt(route.query.page) : 0;
    this.loadPosts(page); // 첫 페이지 로딩
  },
  setup() {
    const router = useRouter(); // router 객체 가져오기

    const handleCreatePost = () => {
      router.push('/CreatePost'); // 게시글 등록 페이지로 이동
    };

    return {
      handleCreatePost
    };
  },
  methods: {
    // 게시글 목록 불러오기 (페이지네이션 적용)
    loadPosts(page) {
  // 페이지 범위를 벗어나지 않도록 제한
  if (this.totalPages !== 0 && (page < 0 || page >= this.totalPages)) return;

  this.currentPage = page;  // 페이지 값 업데이트
  getPostListByBoard(this.boardId, this.currentPage, this.pageSize, 
    (response) => {
      console.log("게시글 목록 불러오기 성공:", response.data);

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
      console.log("이전페이지 이동");
    if (this.currentPage > 0 ) { // 첫 페이지가 아닌 경우에만 실행
       this.loadPosts(this.currentPage - 1); // 이전 페이지로 이동
      }
    },
    // 다음 페이지 로딩
    loadNextPage() {
    console.log("다음페이지 이동");
    if (!this.isLastPage) { // 마지막 페이지가 아닌 경우에만 실행
      this.loadPosts(this.currentPage + 1); // 다음 페이지로 이동
      }
    },
    // 게시글 상세 페이지로 이동
    viewPost(postId) {
      this.$router.push({ name: 'PostDetail', params: { postId } });
    },
    // 날짜 형식 변환 함수
    formatDate(dateString) {
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    },
  }
};
</script>

<style scoped>
.board-list {
  margin: 20px;
}

.common-buttons {
  margin-bottom: 20px;
}

.w3-table-all {
  width: 100%;
  border-collapse: collapse;
}

.w3-table-all th, .w3-table-all td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: center;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.pagination button {
  margin: 0 10px;
  padding: 5px 10px;
}

.pagination span {
  font-size: 16px;
  line-height: 30px;
}
</style>
