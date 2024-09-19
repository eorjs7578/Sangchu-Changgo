<template>
  <div class="container">
    <NavBar />
    <router-view />
  </div>
</template>

<script>
import NavBar from '@/components/NavBar.vue';

export default {
  components: {
    NavBar
  }
};
</script>

<style scoped>
.container {
  display: flex;

}
</style>

<style>
.container {
  padding: 0;
  margin: 0;

}
</style>
