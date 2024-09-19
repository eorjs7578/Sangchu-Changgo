<template>
    <Line :chart-data="chartData" :chart-options="chartOptions" />
  </template>
  
  <script>
  import { Line } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale } from 'chart.js'
  
  ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale)
  
  export default {
    name: 'HourlyVisitorChart',
    components: { Line },
    data() {
      return {
        chartData: {
          labels: ['07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21'],
          datasets: [
            {
              label: 'Hourly Visitors',
              backgroundColor: '#36A2EB',
              data: [50, 100, 150, 200, 300, 350, 400, 450, 500, 550, 500, 450, 400, 300, 200]
            }
          ]
        },
        chartOptions: {
          responsive: true,
          maintainAspectRatio: false
        }
      }
    }
  }
  </script>