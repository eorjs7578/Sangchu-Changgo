<template>
  <div class="map-container">
    <div id="map">
    
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'
let map = null;
onMounted(() => {
 
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    /* global kakao */
    script.onload = () => kakao.maps.load(initMap);
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAOMAP_KEY
    }&libraries=services,clusterer`;
    document.head.appendChild(script);
  }

});


const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(37.5665, 126.9780),
    level:8,
  };
  map = new kakao.maps.Map(container, options);

};
</script>

<style scoped>
.map-container {
    
  position: relative;
}
#map {
  width: 90vw;
  height: 100vh;
  position: relative;
  overflow: hidden;
}
</style>