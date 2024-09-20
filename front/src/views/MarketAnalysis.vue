<template>
  <div class="map-container">
    <Map></Map>
  </div>
</template>
<script setup>
import Map from "@/components/Map.vue";
</script>
<script>
export default {
  components: {
    Map,
  },
};
</script>

<style scoped>
.map-container{
  padding: 0px;
  width: 88vw;
  height: 100vh;
  float: right;
}
</style>