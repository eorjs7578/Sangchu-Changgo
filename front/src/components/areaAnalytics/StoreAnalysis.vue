<template>
  <div id="storeAnalytics" >
    <div class="card shadow-sm p-3 mb-3">
      <h2 class="title">선택 상권 총 점포 수</h2>
      <p class="description">
      선택하신 업종 
      <span class="dropdown">
        <span class="selected" @click="toggleDropdown">{{ selectedServiceType.service_name }}</span>
        <ul class="dropdown-menu" v-if="isDropdownOpen">
          <li v-for="location in serviceTypes" :key="location.code" @click="selectLocation(location)">
            {{ location.service_name }}
          </li>
        </ul>
      </span>
      과 유사한 업종 점포가 <span class="highlight">{{ storeCount }}</span> 개 있어요.
    </p>
    </div>
  </div>
</template>


<script setup>
import { ref, watch,onMounted,defineProps } from 'vue'
import {getAreaServiceType} from '@/api/region'
import {getSimilarServiceCount} from '@/api/analytic'
const areaCode = ref(3001491);

const emit = defineEmits(['update:location'])

const selectedServiceType = ref()
const isDropdownOpen = ref(false)
const serviceTypes = ref([]) 
const storeCount = ref()

const toggleDropdown = () => {
  isDropdownOpen.value = !isDropdownOpen.value
}

const selectLocation = (location) => {
  selectedServiceType.value = location
  isDropdownOpen.value = false
  getSimilarServiceCount(
    areaCode.value, 
    selectedServiceType.value.service_code,
    ({data}) => {
      console.log(data)
      storeCount.value = data
    },
    (error) => {
      console.log(error)
    }
  )
  emit('update:location', location)
}

// watch(() => props.initialLocation, (newValue) => {
//   selectedLocation.value = newValue
// })

onMounted(() => {
  getAreaServiceType(
    areaCode.value,
    ({data}) => {
      console.log(data)
      serviceTypes.value = data
      selectedServiceType.value = data[0]
    },
    (error) => {
      console.log(error)
    }
  )
})
</script>

<style scoped>
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
  min-width: 160px;
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

