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
import { onMounted } from "vue";

const store = useAccountStore();
onMounted(() => {
  store.checkAuthStatus()
})
// 어디든 마운트 될 때마다 인증 정보를 체크
// 인증 정보는 store.userInfo에 저장
// true-false는 간단하게 isAUthenticated에서 확인

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
