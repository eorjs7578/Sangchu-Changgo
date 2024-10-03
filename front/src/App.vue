<template>
  <div class="d-flex">
    <NavBar />
    <main class="flex-grow-1 overflow-auto">
      <router-view />
    </main>
  </div>
</template>

<script setup>
import NavBar from './components/NavBar.vue';
</script>

<style>
html, body {
  height: 100%;
  margin: 0;
}
#app {
  height: 100%;
}
.d-flex {
  height: 100%;
}
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
</style>

<!-- <template>
    <div class="App_container">
        <NavBar />
        <router-view />
    </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue';
import 'bootstrap-icons/font/bootstrap-icons.css';
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

 -->
