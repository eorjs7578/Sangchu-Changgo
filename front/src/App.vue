<template>
  <div>
    <div>
      테스트용
    </div>
    <form @submit.prevent="createBoard">
      <input type="text" placeholder="title"/>
      <input type="text" placeholder="description"/>
      <input type="submit" />
    </form>
  </div>
</template>

<script lang="ts">
// import api from "@/lib/axios";
// import {ref} from "vue";
//
// const title = ref<string>("");
// const description = ref<string>("");
//
// function createBoard() {
//   api.post("/board/create", {
//     title: title.value,
//     description: description.value,
//   })
// }
</script>

<style scoped>

</style>
