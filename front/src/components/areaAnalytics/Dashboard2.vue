<template>
  <div>
    <div class="container-fluid h-100">
      <div class="row bg-success text-white p-3 align-items-center">
        <div class="col-10">
          <h2 class="font-weight-bold">{{ area }}</h2>
        </div>
        <div class="col-2 text-right">
          <button class="btn" @click="toggleFavorite">
            {{ favorite ? "♥" : "♡" }}
          </button>
        </div>
      </div>

      <nav class="nav nav-pills nav-fill my-3">
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'populationAnalysis' }"
            @click.prevent="scrollToSection('populationAnalysis')"
        >
          인구
        </a>
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'storeAnalysis' }"
            @click.prevent="scrollToSection('storeAnalysis')"
        >
          점포수
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
            :class="{ active: activeSection === 'rent' }"
            @click.prevent="scrollToSection('rent')"
        >
          임대료
        </a>
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'surveyForm' }"
            @click.prevent="scrollToSection('surveyForm')"
        >
          평가하기
        </a>
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'surveyResult' }"
            @click.prevent="scrollToSection('surveyResult')"
        >
          평가 결과
        </a>
      </nav>

      <div
          ref="scrollContainer"
          class="overflow-auto custom-scroll h-75"
      >

        <!-- 인구 정보 -->
        <PopulationAnalysis :place="place" />

        <!-- 점포 분석 -->
        <StoreAnalysis :place="place" @update:location="handleLocationUpdate" />

        <!-- 매출 분석 -->
        <SalesAnalysis :place="place" :service="service" />

        <!-- 평가 결과 -->
        <SurveyResult
            :place
        />

        <!--평가 폼-->
        <SurveyForm
            :place
        />

      </div>
    </div>

    <!-- Login Modal -->
    <LoginModal v-if="showLoginPopup" />
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import PopulationAnalysis from "@/components/areaAnalytics/PopulationAnalysis.vue";
import SalesAnalysis from "@/components/areaAnalytics/SalesAnalysis.vue";
import StoreAnalysis from "@/components/areaAnalytics/StoreAnalysis.vue";
import LoginModal from "@/components/login/LoginModal.vue"; // Import the LoginModal
import { useRouter } from "vue-router";
import {api} from "@/lib/api.js";
import SurveyForm from "./SurveyForm.vue";
import SurveyResult from "./SurveyResult.vue";

const props = defineProps({
  place: String,
});


const area = ref("로딩중 입니다.");
const favorite = ref(false);
const showLoginPopup = ref(false); // Flag for showing login modal
const activeSection = ref('');
const scrollContainer = ref(null);
const service = ref();
const favoriteAreaId = props.place; // Use the place as the favoriteAreaId

const router = useRouter();

const checkFavoriteStatus = async () => {
  try {
    const response = await api.get(`/api/favorite/areas/${favoriteAreaId}`);
    favorite.value = response.data; // Set favorite status based on the response
  } catch (error) {
    if (error.response && error.response.status === 401) {
      showLoginPopup.value = true; // Show login modal if unauthorized
    } else {
      console.error("Error checking favorite status:", error);
    }
  }
};

const toggleFavorite = async () => {
  try {
    if (favorite.value) {
      // If currently favorited, send DELETE request to remove favorite
      await api.delete(`/api/favorite/areas/${favoriteAreaId}`);
    } else {
      // If not favorited, send POST request to add favorite
      await api.post('/api/favorite/areas', { areaId: favoriteAreaId });
    }
    favorite.value = !favorite.value; // Toggle the favorite state in the UI
  } catch (error) {
    if (error.response && error.response.status === 401) {
      showLoginPopup.value = true; // Show login modal if unauthorized
    } else {
      console.error("Error toggling favorite status:", error);
    }
  }
};

const handleLocationUpdate = (location) => {
  console.log('Selected location received from child:', location);
  service.value = location;
};

const scrollToSection = (section) => {
  activeSection.value = section;
  const element = document.getElementById(section);
  if (element) {
    element.scrollIntoView({ behavior: "smooth", block: "start" });
  }
};

const updateActiveSection = () => {
  const container = scrollContainer.value;
  
  const sections = ['populationAnalysis', 'storeAnalysis', 'salesAnalysis', 'rent', 'surveyForm', 'surveyResult'];
  sections.forEach(section => {
    const element = document.getElementById(section);
    if (element) {
      const rect = element.getBoundingClientRect();
      // 내부 스크롤 컨테이너의 상대적인 위치를 계산
      const containerRect = container.getBoundingClientRect();
      if (rect.top >= containerRect.top && rect.top < containerRect.bottom) {
        activeSection.value = section;
      }
    }
  });
};

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
</style>
