<template>
  <Bar :chart-data="chartData" :chart-options="chartOptions" />
</template>

<script>
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: 'QuarterlyVisitorChart',
  components: { Bar },
  setup() {
    const chartData = {
      labels: ['2023 - 1분기', '2023 - 2분기', '2023 - 3분기', '2023 - 4분기'],
      datasets: [
        {
          label: 'Quarterly Visitors',
          backgroundColor: '#4C6EF5',
          data: [3000, 3500, 3300, 3100]
        }
      ]
    }

    const chartOptions = {
      responsive: true,
      maintainAspectRatio: false
    }

    return { chartData, chartOptions }
  }
}
</script>
