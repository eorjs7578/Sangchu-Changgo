<template>
    <div class="App_container">
        <NavBar />
        <router-view />
    </div>
</template>

<script setup>
import NavBar from '@/components/NavBar.vue';
import 'bootstrap-icons/font/bootstrap-icons.css';
import { useAccountStore } from '@/stores/useAccountStore.js';

const store = useAccountStore();
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
