<template>
  <div>
    <div id="map">
    
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue'

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
    center: new kakao.maps.LatLng(35.8656, 128.59373),
    level: 6,
  };
  map = new kakao.maps.Map(container, options);

};
</script>

<style scoped>

</style>