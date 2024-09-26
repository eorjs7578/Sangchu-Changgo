<template>
  <div class="card-body">
    <h6 class="card-subtitle mb-2 text-muted">{{ franchise.gu.name }} {{ franchise.dong.name }} {{ franchise.name }}</h6>
      <h4 class="card-title">예상 창업 비용은 <p class="text-primary">{{ formatCurrency(totalCost) }}</p> 입니다.</h4>
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
import {ref,defineProps, computed} from 'vue'

const props = defineProps(['franchise'])

const totalCost = computed(() => franchise.costs.reduce((sum, cost) => sum + cost.amount, 0))

const franchise = props.franchise

// 숫자를 '억', '만원' 형식으로 변환하는 함수
function formatCurrency(value) {
  if (value >= 10000) {
    const billions = Math.floor(value / 10000);
    const remainder = value % 10000;
    return `${billions}억 ${remainder.toLocaleString()}만원`;
  } else {
    return `${(value).toLocaleString()}만원`;
  }
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
  color : pink;
}
a:visited {
  color : black;
}
a:hover {
  color : red;
}
</style>