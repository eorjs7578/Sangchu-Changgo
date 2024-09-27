<template>
  <div>

    <div class="container-fluid">
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
            :class="{ active: activeSection === 'population' }"
            @click.prevent="scrollToSection('population')"
        >
          인구
        </a>
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'storeAnalytics' }"
            @click.prevent="scrollToSection('storeAnalytics')"
        >
          점포수
        </a>
        <a
            class="nav-item nav-link"
            :class="{ active: activeSection === 'analysis' }"
            @click.prevent="scrollToSection('analysis')"
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
            :class="{ active: activeSection === 'review' }"
            @click.prevent="scrollToSection('review')"
        >
          리뷰
        </a>
      </nav>
  
      <div
          ref="scrollContainer"
          class="overflow-auto custom-scroll"
          style="max-height: 400px;"
      >
  
        <!--인구 정보-->
        <PopulationAnalysis
            :place
        />
  
        <!--점포 분석-->
        <StoreAnalysis
          :place
        />
        
        <!--매출 분석-->
        <SalesAnalysis
          :place
        />
  
        <!---->
  
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import PopulationAnalysis from "@/components/areaAnalytics/PopulationAnalysis.vue";
import SalesAnalysis from "@/components/areaAnalytics/SalesAnalysis.vue";
import StoreAnalysis from "@/components/areaAnalytics/StoreAnalysis.vue";
import { useRouter } from "vue-router";
import {api} from "@/lib/api.js";

const props = defineProps({
  place: String,
});


const area = ref("로딩중 입니다.");
const favorite = ref(false);
const activeSection = ref('');
const scrollContainer = ref(null);

const router = useRouter();

const toggleFavorite = () => {
  favorite.value = !favorite.value;
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
  const sections = ['population', 'storeAnalytics', 'analysis', 'rent', 'review'];

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

  console.log(props.place)
  api.get("/api/area-info", {params: {
    areaId: props.place
    }})
      .then(response => {
        console.log(response)
        area.value = response.data.area_name
      })
      .catch(err => {
        console.log(err)
      })
});

</script>

<style>
.custom-scroll {
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* IE와 Edge */
}

.custom-scroll::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}

.nav-link.active {
  background-color: #007bff; /* 활성화 색상 */
  color: white; /* 텍스트 색상 */
}
</style>
