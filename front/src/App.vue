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
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.container {
  padding: 0;
  margin: 0;

}
</style>
