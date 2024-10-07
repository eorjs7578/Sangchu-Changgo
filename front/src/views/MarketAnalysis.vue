<template>
  <div class="map">
    <Map :areaId="areaId"></Map>
  </div>
</template>
<script setup>
import Map from "@/components/Map.vue";

const props = defineProps({
  areaId: {
    type: String,
    required: false
  }
});

</script>
<script>
export default {
  components: {
    Map,
  },
};
</script>

<style scoped>
.map{
  padding: 0px;
  width: 80vw;
  height: 100vh;
  float: right;
}
</style>