<template>

</template>

<script setup>
  import { useAccountStore } from "@/stores/useAccountStore.js"
  import {onMounted} from "vue";
  import {useRouter} from "vue-router";

  const store = useAccountStore();
  const router = useRouter();

  onMounted(() => {
    store.checkAuthStatus().then((response) => {
      // store.isAuthenticated = true;
      store.userInfo = response.data
      router.push("/")

    })
  })
</script>

<style scoped>

</style>