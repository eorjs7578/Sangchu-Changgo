<template>
  <div>
    <div class="container-fluid h-100">
      <div class="row bg-success text-white p-3 align-items-center">
        <div class="col-10 g-1">
          <h4
              class="font-weight-bold"
              :title="area"
          >{{ parsingString(area) }}</h4>
        </div>
        <div class="col-2 g-1 text-right d-flex justify-content-end">
          <button class="btn" @click="toggleFavorite">
            <img
                :src="favorite ? starFilled : starEmpty"
                alt="Favorite"
                class="favorite-icon"
            />
          </button>
          <button class="btn" @click="closeModal">
            <img :src="xIcon" alt="Close" class="close-icon" />
          </button>
        </div>
      </div>

      <nav class="nav nav-pills nav-fill my-3">
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'storeAnalysis' }"
            @click.prevent="scrollToSection('storeAnalysis')"
        >
          점포수
        </a>
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'populationAnalysis' }"
            @click.prevent="scrollToSection('populationAnalysis')"
        >
          인구
        </a>
       
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'salesAnalysis' }"
            @click.prevent="scrollToSection('salesAnalysis')"
        >
          매출분석
        </a>
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'survey' }"
            @click.prevent="scrollToSection('survey')"
        >
          평가
        </a>
      </nav>

      <div
          ref="scrollContainer"
          class="overflow-auto custom-scroll h-75"
      >
        <!-- 점포 분석 -->
        <StoreAnalysis :place="place" @update:location="handleLocationUpdate" />
        <!-- 인구 정보 -->
        <PopulationAnalysis :place="place" />

        <!-- 매출 분석 -->
        <SalesAnalysis :place="place" :service="service" />

        <!-- 평가 결과 -->
        <SurveyResult
            :place="place"
        />

        
        <div v-if="!isAuthenticated" class="h-50">

        </div>

      </div>
    </div>

    <!-- Login Modal -->
    <LoginModal v-if="showLoginPopup" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import PopulationAnalysis from "@/components/areaAnalytics/PopulationAnalysis.vue";
import SalesAnalysis from "@/components/areaAnalytics/SalesAnalysis.vue";
import StoreAnalysis from "@/components/areaAnalytics/StoreAnalysis.vue";
import LoginModal from "@/components/login/LoginModal.vue"; // Import the LoginModal
import { useRouter } from "vue-router";
import {api} from "@/lib/api.js";

import SurveyResult from "./SurveyResult.vue";
import starEmpty from '@/assets/img/star.png';
import starFilled from '@/assets/img/filled_star.png';
import { useAccountStore } from "@/stores/useAccountStore";
import xIcon from '@/assets/img/xIcon.png';

const store = useAccountStore();
const isAuthenticated = ref(store.isAuthenticated); // store의 인증 상태 확인
const props = defineProps({
  place: String,
});

const emit = defineEmits(["closeModal"])
const area = ref("");
const favorite = ref(false);
const showLoginPopup = ref(false); // Flag for showing login modal
const activeSection = ref('storeAnalysis');
const scrollContainer = ref(null);
const service = ref();
const areaId = props.place; // Use the place as the favoriteAreaId

const accountStore = useAccountStore(); // Use the account store

const router = useRouter();
const favoriteAreaId = ref(null);

const checkFavoriteStatus = async () => {
  console.log(accountStore.isAuthenticated);

  if (!accountStore.isAuthenticated) {
    favorite.value = false;
    return;
  }

  try {
    const response = await api.get(`/api/favorite/areas/${areaId}`);
    const { is_check: isCheck, favorite_area_id: responseAreaId } = response.data;

    console.log(response.data); // 응답 확인

    // favorite_area_id가 숫자이므로, ref를 통해 저장하지 않고 직접 할당
    favoriteAreaId.value = responseAreaId; // favoriteAreaId는 숫자로 처리
    favorite.value = isCheck; // 즐겨찾기 상태 업데이트

    console.log(favoriteAreaId.value);
    console.log(favorite.value);
  } catch (error) {
    console.error("Error fetching favorite status:", error);
  }
};


// try {
//     const response = await api.get(`/api/favorite/areas/${areaId}`);
//     const { isCheck, favoriteAreaId: responseAreaId } = response.data;
//     favorite.value = isCheck;
//   } 
//   catch (error) {
//     if (error.response && error.response.status === 401) {
//       showLoginPopup.value = true; // Show login modal if unauthorized
//     } else {
//       console.error("Error checking favorite status:", error);
//     }
//   }

function closeModal() {
  emit("closeModal")
}

const toggleFavorite = async () => {
  if (!accountStore.isAuthenticated) {
    showLoginPopup.value = true;
    return;
  }
  console.log(favorite.value)

  try {
    if (favorite.value) {
      // If currently favorited, send DELETE request to remove favorite
      await api.delete(`/api/favorite/areas/${favoriteAreaId.value}`);
    } else {
      console.log("Sending areaId:", areaId);
      // If not favorited, send POST request to add favorite
      const response = await api.post('/api/favorite/areas', { area_id: areaId });
      console.log(response)
      favoriteAreaId.value = response.data; // 응답에서 favorite_id 받아오기

    }
    favorite.value = !favorite.value; // Toggle the favorite state in the UI
  } catch (error) {
    if (error.response && error.response.status === 401) {
      showLoginPopup.value = true; // Show login modal if unauthorized
    } else {
      console.error("Error toggling favorite status:", error);
      console.log(favoriteAreaId, areaId);
    }
  }
};

const handleLocationUpdate = (location) => {
  console.log('Selected location received from child:', location);
  service.value = location;
};

const scrollToSection = (section) => {
  activeSection.value = section;
   console.log('Scrolling to section:', section);
  const element = document.getElementById(section);
  if (element) {
    element.scrollIntoView({ behavior: "smooth", block: "start" });
  }
};

const updateActiveSection = () => {
  const container = scrollContainer.value;
  
  const sections = ['populationAnalysis', 'storeAnalysis', 'salesAnalysis', 'survey'];
  sections.forEach(section => {
    const element = document.getElementById(section);
    if (element) {
      const rect = element.getBoundingClientRect();
      // 내부 스크롤 컨테이너의 상대적인 위치를 계산
      const containerRect = container.getBoundingClientRect();
      if (rect.top+1 >= containerRect.top && rect.top+1 < containerRect.bottom) {
        activeSection.value = section;
      }
    }
  });
};

function parsingString(str) {
  if (str.length > 9) {
    return str.substring(0,9) + "..."
  }
  return str
}

watch([area], () => {
  console.log("watch")
  const container = scrollContainer.value;
  container.addEventListener('scroll', updateActiveSection);

  api.get("/api/area-info", {
    params: {
      areaId: props.place,
    }
  })
    .then(response => {
      area.value = response.data.area_name;
    })
    .catch(err => {
      console.error("Error fetching area info:", err);
    });

  // Check if the area is a favorite when component mounts
  checkFavoriteStatus();
})

onMounted(() => {
  const container = scrollContainer.value;
  container.addEventListener('scroll', updateActiveSection);

  api.get("/api/area-info", {
    params: {
      areaId: props.place,
    }
  })
    .then(response => {
      area.value = response.data.area_name;
    })
    .catch(err => {
      console.error("Error fetching area info:", err);
    });

  // Check if the area is a favorite when component mounts
  checkFavoriteStatus();
});
</script>

<style>
.custom-scroll {
  scrollbar-width: none;
  -ms-overflow-style: none;
}

.custom-scroll::-webkit-scrollbar {
  display: none;
}

.nav-link.active {
  background-color: #007bff;
  color: white;
}

.favorite-icon {
    max-width: 30px; /* 원하는 너비 설정 */
    max-height: 30px; /* 원하는 높이 설정 */
    width: auto;
    height: auto;
  }
.section {
  margin-bottom: 100px; /* 섹션 간의 간격을 충분히 확보 */
}

.close-icon {
  max-width: 20px; /* 너비를 20px로 설정, 원하는 크기로 변경 가능 */
  max-height: 20px; /* 높이를 20px로 설정 */
  /* margin-left: 20px;  */
}
</style>
