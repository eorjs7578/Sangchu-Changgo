<template>
  <div class="card-body">
    <h6 class="card-title">{{ franchise.name }}</h6>
    <p class="card-text">{{ franchise.cost.toLocaleString() }}원</p>
    <table class="table table-sm table-borderless">
      <tbody>
        <tr v-for="(cost, costIndex) in franchise.costs" :key="costIndex">
          <td>{{ cost.name }}</td>
          <td class="text-end">{{ cost.amount.toLocaleString() }}원</td>
        </tr>
      </tbody>
    </table>
    </div>
</template>

<script setup>
import {ref} from 'vue'

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