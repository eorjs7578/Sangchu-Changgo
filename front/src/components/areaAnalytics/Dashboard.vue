<template>
  <div class="container-fluid">
    <div class="row bg-success text-white p-3 align-items-center">
      <div class="col-10">
        <h2 class="font-weight-bold">신사은행 나무공원</h2>
      </div>
      <div class="col-2 text-right">
        <!-- 하트 버튼 -->
        <button class="btn btn-outline-light" @click="toggleFavorite">
          <i :class="favorite ? 'bi bi-heart-fill' : 'bi bi-heart'"></i>
        </button>
      </div>
    </div>

    <nav class="nav nav-pills nav-fill my-3">
      <a class="nav-item nav-link active" href="#">인구</a>
      <a class="nav-item nav-link" href="#">점포수</a>
      <a class="nav-item nav-link" href="#">매출분석</a>
      <a class="nav-item nav-link" href="#">임대료</a>
      <a class="nav-item nav-link" href="#">리뷰</a>
    </nav>

    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h5 class="card-title">유동인구</h5>
      </div>
    </div>

    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <div class="mb-3">
          <h3 class="card-subtitle text-muted">일일 평균 유동인구</h3>
          <p class="display-4 font-weight-bold text-primary">
            4,110,178<span class="h6">명 입니다.</span>
          </p>
        </div>
      </div>
    </div>

    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h5 class="card-title">상세 정보</h5>
        <!-- 추가적인 정보를 여기에 넣을 수 있습니다 -->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Dashboard",
  data() {
    return {
      favorite: false, // 좋아요 여부를 나타내는 데이터
    };
  },
  methods: {
    toggleFavorite() {
      this.favorite = !this.favorite; // 버튼을 클릭하면 좋아요 상태 변경
    },
  },
};
</script>

<style scoped>
.nav-link.active {
  background-color: #007bff;
  color: white;
}

.card-title {
  font-size: 1.25rem;
  font-weight: bold;
}

.display-4 {
  font-size: 2.5rem;
}

.card-subtitle {
  font-size: 1rem;
}

button i {
  font-size: 1.5rem;
}

button:focus {
  outline: none;
}

.bi-heart-fill {
  color: red;
}
</style>
