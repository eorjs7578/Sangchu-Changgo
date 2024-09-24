<template>
  <div>
    <Dashboard2
        :place="place"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import Dashboard2 from "@/components/areaAnalytics/Dashboard2.vue";

const route = useRoute();
const place = ref(route.params.id);

</script>

<style scoped>

</style>
