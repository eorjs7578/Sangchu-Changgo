<template>
  <div class="map-container">
    <div id="map">
    </div>
    <Dashboard2
     v-if="showModal" 
     class="modal-overlay"
     :place="computedPlace"
     :key="computedPlace"
     @click.self="closeModal"
     >
    </Dashboard2>

</div>
</template>

<script setup>
import {
    getGu,
    getDong,
    getArea,
    getAreaInfo,
} from "@/api/polygon.js";
import Dashboard2 from "@/components/areaAnalytics/Dashboard2.vue";

const props = defineProps({
  areaId: {
    type: String,
    required: false,
    default: null
  }
});

import {ref, onMounted, watch,computed} from 'vue'
let map = null;
let areas=ref([]);
let polygons = ref([]);
let script=null;
let mapLevel=null;
let x = null;
let y = null;
let place = ref();
let dongId = ref();
let markers = [];
let customOverlay = null;
const prevArea = ref();
const prevDong = ref();
const prevGu = ref();

const selectedArea = ref({ name: '', size: 0 }); // 선택된 영역 정보를 저장하는 ref
const showModal = ref(false); // 모달 표시 여부를 저장하는 ref

onMounted(() => {
    // 구 정보 불러오기 
    if(props.areaId != null){
        console.log("aaa")
        loadArea(props.areaId);
    }else{
        console.log("bbb")
        getGuData();
    }
});

async function loadArea(areaId){
    let loadData;
    await getAreaInfo(areaId)
    .then(data=>{
        loadData = data;
    })
    .catch(error=>{
        console.error("Error:", error);
    })

    await getGu()
    .then(data=>{
        prevGu.data = data;
        console.log(prevGu.data)
    })
    .catch(error=>{
        console.error("Error:", error);
    })

    await getDong(loadData.guCode)
    .then(data=>{
        prevDong.data = data;
    })
    .catch(error=>{
        console.error("Error:", error);
    })

    await getArea(loadData.dongCode)
    .then(data => {
        prevArea.data = data;
        areas = data;
    })
    .catch(error=>{
        console.error("Error:", error);
    })

    mapLevel=4;
    x = loadData.xpos;
    y=loadData.ypos;
    loadMap(x, y);
}

async function getGuData(){
    mapLevel = 9;
    x = 37.5665;
    y = 126.9780
    await getGu()
    .then(data=>{
        areas = data;
        prevGu.data = data;
        console.log(prevGu.data)
    })
    .catch(error=>{
        console.error("Error:", error);
    })
    loadMap(x, y);
}

async function getDongData(code){
    mapLevel=6;
    await getDong(code)
    .then(data=>{
        prevDong.data = data;
        areas = data;
    })
    .catch(error=>{
        console.error("Error:", error);
    })
    loadMap(x, y);
}

async function getAreaData(code){
    mapLevel=4;
    await getArea(code)
    .then(data => {
        prevArea.data = data;
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
    disableDoubleClickZoom: true,
  };
  map = new kakao.maps.Map(container, options);
  customOverlay = new kakao.maps.CustomOverlay({})
  drawPolygons();

  kakao.maps.event.addListener(map, 'zoom_changed', () => {
    const lev = map.getLevel()
    if (lev <= 4) {
      areas = prevArea.data
      hideMarkers()
      drawPolygons()
    }

    if (lev === 6) {
      if (areas !== prevDong.data) {
        areas = prevDong.data
        hideMarkers()
        drawPolygons()
      }
    }
    if (lev >= 8) {
      if (areas !== prevGu.data) {
        areas = prevGu.data
        hideMarkers()
        drawPolygons()
      }
    }

  })
};

// "마커 감추기" 버튼을 클릭하면 호출되어 배열에 추가된 마커를 지도에서 삭제하는 함수입니다
function hideMarkers() {
    setMarkers(null);   
}

// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
function setMarkers(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }            
}

function drawPolygons(){
    
    polygons.value.forEach(polygon =>{
        polygon.setMap(null);
    });
    polygons.value=[];

    if (areas && areas.length > 0) {
        for (let i = 0; i < areas.length; i++) {
            console.log("이거봐!!" + areas[i].name)
            // 마커를 생성합니다
            const polygon = createPolygon(areas[i]);
            polygons.value.push(polygon);  // 생성한 폴리곤을 배열에 저장
        }
    }
}

function parsePolygon(polygonStr) {
    if (polygonStr.startsWith("POLYGON")) {
        // POLYGON 처리
        return parseSinglePolygon(polygonStr);
    } else if (polygonStr.startsWith("MULTIPOLYGON")) {
        // MULTIPOLYGON 처리
        return parseMultiPolygon(polygonStr);
    } else {
        console.error("Unknown polygon format: ", polygonStr);
        return [];
    }
}

// 단일 POLYGON 문자열을 처리하여 LatLng 배열로 변환하는 함수
function parseSinglePolygon(polygonStr) {
    const coordinates = polygonStr
        .replace("POLYGON ((", "")  // POLYGON (( 제거
        .replace("))", "")  // )) 제거
        .split(", ");  // 좌표들을 분리

    const path = coordinates.map(coord => {
        const [lng, lat] = coord.split(" ").map(Number);
        return new kakao.maps.LatLng(lat, lng);  // LatLng 객체로 변환
    });

    return [path]; // 단일 POLYGON이므로 하나의 배열을 반환
}

// MULTIPOLYGON 문자열을 처리하여 LatLng 배열로 변환하는 함수
function parseMultiPolygon(multiPolygonStr) {
    // MULTIPOLYGON (((...),(...)),((...),(...))) 형식에서 POLYGON들 추출
    const polygonGroups = multiPolygonStr
        .replace("MULTIPOLYGON (((", "") // MULTIPOLYGON ((( 제거
        .replace(")))", "") // ))) 제거
        .split(")), (("); // 각 POLYGON 그룹을 나눔

    const paths = polygonGroups.map(polygonStr => {
        // POLYGON 그룹 내의 각 경로를 처리
        const coordinates = polygonStr.split(", ");
        return coordinates.map(coord => {
            const [lng, lat] = coord.split(" ").map(Number);
            return new kakao.maps.LatLng(lat, lng);  // LatLng 객체로 변환
        });
    });

    return paths; // MULTIPOLYGON이므로 배열들의 배열을 반환
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
        strokeColor: '#066905',
        strokeOpacity: 0.7,
        fillColor: '#D7F9D6',
        fillOpacity: 0.5 
    });
    if(area.id== props.areaId){
        polygon.setOptions({strokeColor: '#FF0000'});
    }

    // 다각형에 mouseover 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 변경합니다 
    // 지역명을 표시하는 커스텀오버레이를 지도위에 표시합니다
    kakao.maps.event.addListener(polygon, 'mouseover', function(mouseEvent) {
        polygon.setOptions({fillColor: '#066905'});
        customOverlay.setContent('<div class="area">' + area.name + '</div>');
        console.log("mouse over!" + area.name)
        customOverlay.setPosition(mouseEvent.latLng); 
        customOverlay.setMap(map);
    });

    // 다각형에 mousemove 이벤트를 등록하고 이벤트가 발생하면 커스텀 오버레이의 위치를 변경합니다 
    kakao.maps.event.addListener(polygon, 'mousemove', function(mouseEvent) {
        
        customOverlay.setPosition(mouseEvent.latLng); 
    });

    // 다각형에 mouseout 이벤트를 등록하고 이벤트가 발생하면 폴리곤의 채움색을 원래색으로 변경합니다
    // 커스텀 오버레이를 지도에서 제거합니다
    kakao.maps.event.addListener(polygon, 'mouseout', function() {
        polygon.setOptions({fillColor: '#D7F9D6'});
        customOverlay.setMap(null);
    });

    // 다각형에 click 이벤트를 등록하고 이벤트가 발생하면 다각형의 이름과 면적을 인포윈도우에 표시합니다 
    kakao.maps.event.addListener(polygon, 'click', function(mouseEvent) {
        x = area.ypos;
        y = area.xpos;

        console.log('Selected place:', place.value);
           // 선택된 지역 정보를 저장하여 모달에 표시
        selectedArea.value = {
            name: area.name,
            size: area.size,
        };


    // 선택된 지역 정보를 저장하여 모달에 표시
    selectedArea.value = {
      name: area.name,
      size: area.size,
    };

    // 모달을 보이도록 설정

    if(area.size==0){ // 구
        getDongData(area.id);
    }
    else if (area.size ==1){
        getAreaData(area.id);
        dongId = area.id;
    }else{
        place.value = area.id;
        getAreaData(dongId);
            // showModal.value = true;
            console.log("ffff")
            console.log(place.value)

        }
    });

    return polygon;
}

function closeModal() {
  showModal.value = false;
}

watch(place, (newPlace) => {
  if (newPlace) {
    showModal.value = true;
    console.log('Showing modal with updated place:', newPlace); // 업데이트된 place 값 확인
  }
});
const computedPlace = computed(() => place.value);



</script>
<style>
.area {
    position: absolute;
    background: #fff;
    border: 1px solid #888;
    border-radius: 3px;
    font-size: 12px;
    top: -5px;
    left: 15px;
    padding:2px;
}
</style>

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

.modal-overlay {
    position: fixed;
    top: 0;
    left: 12vw;
    width: 25vw;
    height: 100%;
    background: rgb(255, 250, 250);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 1;
}

</style>
