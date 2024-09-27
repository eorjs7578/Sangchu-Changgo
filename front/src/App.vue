<template>
  <div class="App_container">
    <NavBar />
    <router-view />
  </div>
</template>

<script setup>
import NavBar from "@/components/NavBar.vue";
import "bootstrap-icons/font/bootstrap-icons.css";
import { useAccountStore } from "@/stores/useAccountStore.js";

const store = useAccountStore();

store.checkAuthStatus()
// 어디든 마운트 될 때마다 인증 정보를 체크
// 인증 정보는 store.isAuthenticated에 저장
// 로그인 되어 있으면 json 형태로 회원 정보가 나오고, 안되어 있으면 false만 출력됨

</script>

<style scoped>
.App_container {
  display: flex;
  justify-content: flex-start;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}
</style>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

</style>
