<template>
  <div class="card-body">
    <h6 class="card-subtitle mb-2 text-muted">{{ franchise.gu }} {{ franchise.dong }} {{ franchise.name }}</h6>
      <h4 class="card-title">예상 창업 비용은 <p class="text-primary">{{ totalCost.toLocaleString() }}원</p> 입니다.</h4>
        <table class="table table-borderless">
          <tbody>
            <tr v-for="(cost, index) in franchise.costs" :key="index">
              <td>{{ cost.name }}</td>
              <td class="text-end">{{ cost.amount.toLocaleString() }}원</td>
              </tr>
          </tbody>
        </table>
    </div>
</template>

<script setup>
import {ref,defineProps, computed} from 'vue'

const props = defineProps(['franchise'])

const totalCost = computed(() => franchise.costs.reduce((sum, cost) => sum + cost.amount, 0))

const franchise = props.franchise

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
</style>