<template>
  <div class="card-body position-relative">
    <div class="favorite-icon" @click.stop="toggleFavorite">
      <i :class="['bi', isFavorite ? 'bi-star-fill text-warning' : 'bi-star']"></i>
    </div>
    <h6 class="card-subtitle mb-2 text-muted">{{ franchise.gu.name }} {{ franchise.dong.name }} {{ franchise.name }} ({{ franchise.storeSize }}평, {{ franchise.floor }})</h6>
    <h4 class="card-title">예상 창업 비용은 <p></p> <span class="text-primary">{{ formatCurrency(totalCost) }}</span> 입니다.</h4>
    <table class="table table-borderless">
      <tbody>
        <tr v-for="(cost, index) in franchise.costs" :key="index">
          <td>{{ cost.name }}</td>
          <td class="text-end">{{ formatCurrency(cost.amount) }}</td>
        </tr>
      </tbody>
    </table>
    <a v-bind:href="franchise.link" target="_blank">상세정보보기</a>
  </div>
</template>

<script setup>
import { ref, defineProps, computed } from 'vue'
import { deleteFavoriteFranchise, addFavoriteFranchise} from '@/api/franchise.js'

const props = defineProps(['franchise'])

const totalCost = computed(() => props.franchise.costs.reduce((sum, cost) => sum + cost.amount, 0))

const franchise = props.franchise

const isFavorite = ref(franchise.likeId > 0);

const myFranchise = ref({
      dong_code : franchise.dong.code,
      franchise_id: franchise.id,
      store_size : franchise.storeSize,
      floor : franchise.floor == 1 ? true : false
})

// 숫자를 '억', '만원' 형식으로 변환하는 함수
function formatCurrency(value) {
  if (value >= 100000) {
    let result = '';
    const billions = Math.floor(value / 100000);
    result += billions.toString() + '억 '
    const remainder = Math.floor(value % 100000);
    if(remainder > 0) {
      result += (Math.floor(remainder/10)).toLocaleString() + '만원'
    }
    return result;
  } else {
    return `${(Math.floor(value/10)).toLocaleString()}만원`;
  }
}

const toggleFavorite = () => {
  console.log(isFavorite.value)
  if(isFavorite.value) {
    deleteFavoriteFranchise(
    franchise.likeId,
    ({data}) => {
      isFavorite.value = !isFavorite.value
      console.log(isFavorite)
    },
    (error) => {
      console.log(error)
      
    }
  )
  } else {
    addFavoriteFranchise(
    myFranchise.value,
    ({data}) => {
      console.log(data)
      isFavorite.value = !isFavorite.value
      franchise.likeId = data
    },
    (error) => {
      console.log(error)
      
    }
  )
  }
  
  
  // Here you can add logic to save the favorite status, e.g., emit an event or call an API
  console.log(`Franchise ${franchise.name} favorite status: ${isFavorite.value}`)
}

console.log(franchise)

</script>

<style scoped>
.card {
  cursor: pointer;
  transition: all 0.3s ease;
}

.card:hover {
  box-shadow: 0 0 10px rgba(0,0,0,0.1);
}

a:link {
  color: pink;
}

a:visited {
  color: black;
}

a:hover {
  color: red;
}

.favorite-icon {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 1.5rem;
  cursor: pointer;
}

.favorite-icon:hover {
  transform: scale(1.1);
}
</style>