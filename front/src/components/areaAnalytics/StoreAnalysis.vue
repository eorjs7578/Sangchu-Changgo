<template>
  <div id="storeAnalysis">
    <div class="card shadow-sm mb-3">
      <div class="card-body">
        <h4>선택 상권 총 점포 수</h4>
        <hr>
        <p class="description">
          선택하신 업종
          <span class="dropdown" ref="dropdownRef">
            <span class="selected" @click="toggleDropdown">{{ selectedServiceType.service_name }}</span>
            <ul class="dropdown-menu custom-scroll" v-if="isDropdownOpen" :style="dropdownStyle">
              <li v-for="location in serviceTypes" :key="location.code" @click="selectLocation(location)">
                {{ location.service_name }}
              </li>
            </ul>
          </span>
          과 유사한 업종 점포가 <span class="highlight">{{ storeCount }}</span> 개 있어요.
        </p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, defineProps, computed, nextTick } from 'vue'
import { getAreaServiceType } from '@/api/region'
import { getSimilarServiceCount } from '@/api/analytic'

const props = defineProps(['place']);
const emit = defineEmits(['update:location'])

const place = ref()
const selectedServiceType = ref({
  service_name: 'aa',
  service_code: 1234
})
const isDropdownOpen = ref(false)
const serviceTypes = ref([])
const storeCount = ref()
const dropdownRef = ref(null)

const dropdownStyle = computed(() => {
  if (!dropdownRef.value) return {}
  const rect = dropdownRef.value.getBoundingClientRect()
  return {
    width: `${rect.width}px`,
    maxHeight: '200px',
    overflowY: 'auto'
  }
})

watch(
  () => props.place,
  (newPlace) => {
    place.value = newPlace;
    console.log('Updated franchise list:', place.value);
    getAreaServiceType(
      place.value,
      ({ data }) => {
        console.log(data)
        serviceTypes.value = data
        selectedServiceType.value = data[0]
        emit('update:location', data[0])
      },
      (error) => {
        console.log(error)
      }
    )
  },
  { immediate: true }
);

watch(
  () => selectedServiceType.value,
  (newSelected) => {
    getSimilarServiceCount(
    place.value,
    newSelected.service_code,
    ({ data }) => {
      console.log(data)
      storeCount.value = data
    },
    (error) => {
      console.log(error)
    }
  )
  }
)

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value
  if (isDropdownOpen.value) {
    nextTick(() => {
      const dropdownMenu = dropdownRef.value.querySelector('.dropdown-menu')
      if (dropdownMenu) {
        dropdownMenu.style.width = `${dropdownRef.value.offsetWidth}px`
      }
    })
  }
}

const selectLocation = (location) => {
  selectedServiceType.value = location
  isDropdownOpen.value = false
  getSimilarServiceCount(
    place.value,
    selectedServiceType.value.service_code,
    ({ data }) => {
      console.log(data)
      storeCount.value = data
    },
    (error) => {
      console.log(error)
    }
  )
  emit('update:location', location)
}

onMounted(() => {
  document.addEventListener('click', (event) => {
    if (dropdownRef.value && !dropdownRef.value.contains(event.target)) {
      isDropdownOpen.value = false
    }
  })
})
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@400;500;700&display=swap');

* {
    font-family: 'Noto Sans KR', sans-serif;
}

.button {
    background-color: #17a2b8;
    color: white;
    padding: 10px 20px;
    border-radius: 5px;
    transition: background-color 0.3s ease;
}

.button:hover {
    background-color: #138496;
}

.nav-link {
    color: #333;
    font-size: 1.1rem;
    font-weight: 500;
    transition: background-color 0.3s ease, color 0.3s ease;
}

.nav-link:hover {
    background-color: #e9ecef;
    color: #17a2b8;
    border-radius: 5px;
}
</style>