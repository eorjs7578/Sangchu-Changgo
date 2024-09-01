<template>
  <div>
    <div>
      테스트용
    </div>
    <form @submit.prevent="createBoard">
      <input v-model="title" type="text" placeholder="title" />
      <input v-model="description" type="text" placeholder="description" />
      <input type="submit" />
    </form>
  </div>
</template>

<script lang="ts" setup>
import { ref } from 'vue';
import api from '@/lib/axios';

// Ref로 title과 description 정의
const title = ref<string>("");
const description = ref<string>("");

function createBoard() {
  // POST 요청 보내기
  api.post("/board/create", {
    title: title.value,
    description: description.value,
    userId: 1,
  }).then(response => {
    console.log("Board created:", response.data);
  }).catch(error => {
    console.error("There was an error creating the board:", error);
  });
}
</script>

<style scoped>
/* 스타일링은 여기 추가할 수 있습니다. */
</style>
