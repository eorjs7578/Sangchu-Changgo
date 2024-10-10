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
        console.log("여기봐"+ data)
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

.custom-scroll {
  scrollbar-width: thin;
  scrollbar-color: #888 #f1f1f1;
}

.custom-scroll::-webkit-scrollbar {
  width: 6px;
  display: none;
}

.custom-scroll::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.custom-scroll::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 4px;
}

.custom-scroll::-webkit-scrollbar-thumb:hover {
  background: #555;
}

.store-selector {
  font-family: Arial, sans-serif;
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.title {
  font-size: 1.2em;
  color: #333;
  margin-bottom: 10px;
}

.description {
  font-size: 1em;
  color: #555;
  line-height: 1.5;
}

.dropdown {
  position: relative;
  display: inline-block;
}

.selected {
  color: #007bff;
  cursor: pointer;
  border-bottom: 1px dashed #007bff;
}

.dropdown-menu {
  position: absolute;
  top: 100%;
  left: 0;
  z-index: 1000;
  display: block;
  padding: 5px 0;
  margin: 2px 0 0;
  font-size: 1em;
  color: #212529;
  text-align: left;
  list-style: none;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid rgba(0,0,0,.15);
  border-radius: .25rem;
}

.dropdown-menu li {
  padding: 5px 20px;
  cursor: pointer;
}

.dropdown-menu li:hover {
  background-color: #f8f9fa;
}

.highlight {
  color: #007bff;
  font-weight: bold;
}

</style>