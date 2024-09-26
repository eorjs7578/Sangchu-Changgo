<template>
  <div class="map-container">
    <div id="map">
    
    </div>
  </div>
</template>

<script setup>
import {
    getGu,
    getDong,
    getArea,
} from "@/api/polygon.js";


import {ref, onMounted, watch} from 'vue'
let map = null;
let areas=ref([]);
let polygons = ref([]);
let script=null;
let mapLevel=null;
let x = null;
let y = null;


onMounted(() => {

    mapLevel = 9;
    x = 37.5665;
    y = 126.9780

    // 구 정보 불러오기 
    getGuData();
});

async function getGuData(){

    await getGu()
    .then(data=>{
        areas = data;
    })
    .catch(error=>{
        console.error("Error:", error);
    })
    loadMap(x, y);
}

async function getDongData(code){

    await getDong(code)
    .then(data=>{
        areas = data;
    })
    .catch(error=>{
        console.error("Error:", error);
    })
    loadMap(x, y);
}

async function getAreaData(code){

    await getArea(code)
    .then(data=>{
        areas = data;
    })
    .catch(error=>{
        console.error("Error:", error);
    })
    loadMap(x, y);
}

function loadMap(x,y){
    script = document.createElement("script");
    script.onload = () => kakao.maps.load(()=>initMap(x,y));
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAOMAP_KEY
    }&libraries=services,clusterer`;
    document.head.appendChild(script);
}

const initMap = (x,y) => {
    
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(x, y),
    level:mapLevel,
  };
  map = new kakao.maps.Map(container, options);

  drawPolygons();
};

function drawPolygons(){
    
    polygons.value.forEach(polygon =>{
        polygon.setMap(null);
    });
    polygons.value=[];

    if (areas && areas.length > 0) {
        for (let i = 0; i < areas.length; i++) {
            const polygon = createPolygon(areas[i]);
            polygons.value.push(polygon);  // 생성한 폴리곤을 배열에 저장
        }
    }
}


// polygon 문자열을 처리하여 LatLng 배열로 변환하는 함수
function parsePolygon(polygonStr) {
    const coordinates = polygonStr
        .replace("POLYGON ((", "")  // POLYGON (( 제거
        .replace("))", "")  // )) 제거
        .split(", ");  // 좌표들을 분리

    const path = coordinates.map(coord => {
        const [lng, lat] = coord.split(" ").map(Number);
        return new kakao.maps.LatLng(lat, lng);  // LatLng 객체로 변환
    });

    return path;
}

// 다각형을 생상하고 이벤트를 등록하는 함수입니다
function createPolygon(area) {
 // polygon 문자열을 처리하여 경로(path)를 생성
    const path = parsePolygon(area.polygon);
    // 다각형을 생성합니다 
    var polygon = new kakao.maps.Polygon({
        map: map, // 다각형을 표시할 지도 객체
        path: path,
        strokeWeight: 2,
        strokeColor: '#004c80',
        strokeOpacity: 0.8,
        fillColor: '#fff',
        fillOpacity: 0.7 
    });

    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다 
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
        polygon.setOptions({fillColor: '#09f'});

    });

    // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
    // 커스텀 오버레이를 지도에서 제거합니다 
    kakao.maps.event.addListener(polygon, 'mouseout', function() {
        polygon.setOptions({fillColor: '#fff'});
    }); 

    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다 
    kakao.maps.event.addListener(polygon, 'click', function(mouseEvent) {
        x = area.ypos;
        y = area.xpos;
        // 지도 확대, 폴리곤 다시 그리기
        if(area.size==0){ // 구 
            mapLevel=6;
            getDongData(area.id);
        }
        else if (area.size ==1){
            mapLevel=4;
            getAreaData(area.id);
        }else{

        }
    });

    return polygon; 
}

// watch(areas, () => {
//     loadMap(x, y);  // areas 값이 변경되면 폴리곤을 다시 그림
// },
// { deep: true }
// );

</script>

<style scoped>
.map-container {
    width: 100%;
    height: 100%;
}
#map {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
</style>