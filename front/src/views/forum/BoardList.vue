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
          </tr>
        </thead>
        <tbody>
          <tr v-for="(row, idx) in placeholderList" :key="idx">
            <td>{{ idx + 1 }}</td>
            <td><a @click="fnView(row.idx)">{{ row.title }}</a></td>
            <td>{{ row.author }}</td>
            <td>{{ row.created_at }}</td>
          </tr>
        </tbody>
      </table>
  
      <!-- 페이징 처리 -->
      <div class="pagination w3-bar w3-padding-16 w3-small" v-if="paging.total_list_cnt > 0">
        <span class="pg">
          <a href="javascript:;" @click="fnPage(1)" class="first w3-button w3-bar-item w3-border">&lt;&lt;</a>
          <a href="javascript:;" v-if="paging.start_page > 10" @click="fnPage(paging.start_page - 1)"
             class="prev w3-button w3-bar-item w3-border">&lt;</a>
          <template v-for="(n, index) in paginavigation()">
            <template v-if="paging.page === n">
              <strong class="w3-button w3-bar-item w3-border w3-green" :key="index">{{ n }}</strong>
            </template>
            <template v-else>
              <a class="w3-button w3-bar-item w3-border" href="javascript:;" @click="fnPage(n)" :key="index">{{ n }}</a>
            </template>
          </template>
          <a href="javascript:;" v-if="paging.total_page_cnt > paging.end_page" @click="fnPage(paging.end_page + 1)"
             class="next w3-button w3-bar-item w3-border">&gt;</a>
          <a href="javascript:;" @click="fnPage(paging.total_page_cnt)" class="last w3-button w3-bar-item w3-border">&gt;&gt;</a>
        </span>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        placeholderList: [
          // 임시 데이터
          { idx: 1, title: '게시글 1', author: '작성자1', created_at: '2023-09-26' },
          { idx: 2, title: '게시글 2', author: '작성자2', created_at: '2023-09-25' }
        ],
        paging: {
          block: 0,
          end_page: 0,
          next_block: 0,
          page: 1,
          page_size: 10,
          prev_block: 0,
          start_index: 0,
          start_page: 1,
          total_block_cnt: 1,
          total_list_cnt: 20,  // 예시
          total_page_cnt: 2
        }
      }
    },
    methods: {
      fnWrite() {
        // 등록 버튼 클릭 시 처리할 로직
        console.log("등록 버튼 클릭됨");
      },
      fnView(idx) {
        // 게시글 클릭 시 처리할 로직
        console.log("게시글 ID:", idx);
      },
      fnPage(pageNumber) {
        // 페이징 클릭 시 처리할 로직
        console.log("이동할 페이지:", pageNumber);
      },
      paginavigation() {
        // 페이징 네비게이션
        let pageNumber = [];
        let start_page = this.paging.start_page;
        let end_page = this.paging.end_page;
        for (let i = start_page; i <= end_page; i++) {
          pageNumber.push(i);
        }
        return pageNumber;
      }
    }
  }
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
    text-align: center;
    margin-top: 20px;
  }
  
  .pg a, .pg strong {
    margin: 0 5px;
    padding: 5px 10px;
    display: inline-block;
  }
  </style>
  