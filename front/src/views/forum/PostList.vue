<template>
  <div class="board-list">
    <div class="common-buttons">
      <!-- 등록 버튼 -->
      <button type="button" class="w3-button w3-round w3-blue-gray" @click="fnWrite">등록</button>
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
        <tr v-for="(row, idx) in list" :key="idx">
          <td>{{ idx + 1 }}</td>
          <td><a @click="fnView(row.id)">{{ row.title }}</a></td>
          <td>{{ row.member_id }}</td>
          <td>{{ formatDate(row.created_at) }}</td>
          <td>{{ row.view }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import { listPost } from '@/api/forum'; // API 호출 함수 가져오기
import { useRoute, useRouter } from 'vue-router';  // Vue Router에서 현재 라우트 정보와 router 객체 가져오기

export default {
  data() {
    return {
      list: [] // 서버로부터 받아온 게시글 목록을 저장할 변수
    };
  },
  mounted() {
    const route = useRoute(); // 현재 라우트 정보 가져오기
    let areaId = route.params.areaId || 3001492; // pathVariable인 areaId 추출, 없으면 기본값

    console.log("Area ID to use:", areaId); // areaId가 제대로 설정되었는지 확인

    // listPost를 바로 호출하여 게시글 목록을 가져옴
    listPost(areaId, 
      (response) => {
        console.log("API 응답:", response); // API 응답이 제대로 오는지 확인
        this.list = response.data;  // 서버에서 받아온 데이터를 할당
        console.log("게시글 목록 가져오기 성공:", this.list);
      },
      (error) => {
        console.error('게시판 목록을 불러오는 중 에러 발생:', error);
      }
    );
  },
  setup() {
    const router = useRouter(); // router 객체 가져오기

    const fnWrite = () => {
      router.push('/CreatePost');  // 등록 버튼 클릭 시 create-post 경로로 이동
      console.log("등록 버튼 클릭됨");
    };

    return {
      fnWrite
    };
  },
  methods: {
    fnView(id) {
      // 게시글 클릭 시 postId를 포함한 상세보기 페이지로 이동
      this.$router.push({ name: 'PostDetail', params: { postId: id } });
      console.log("게시글 ID:", id);
    },
    formatDate(dateString) {
      // 날짜 형식 변환 함수 (예시)
      const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
      return new Date(dateString).toLocaleDateString(undefined, options);
    }
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
</style>
