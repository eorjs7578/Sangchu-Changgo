<template>
    <Radar :chart-data="chartData" :chart-options="chartOptions" />
  </template>
  
  <script>
  import { Radar } from 'vue-chartjs'
  import { Chart as ChartJS, Title, Tooltip, Legend, RadialLinearScale, PointElement, LineElement } from 'chart.js'
  
  ChartJS.register(Title, Tooltip, Legend, RadialLinearScale, PointElement, LineElement)
  
  export default {
    name: 'AgeGroupChart',
    components: { Radar },
    data() {
      return {
        chartData: {
          labels: ['20대', '30대', '40대', '50대', '60대 이상'],
          datasets: [
            {
              label: '남성',
              backgroundColor: 'rgba(54, 162, 235, 0.2)',
              borderColor: 'rgba(54, 162, 235, 1)',
              pointBackgroundColor: 'rgba(54, 162, 235, 1)',
              data: [65, 75, 70, 80, 60]
            },
            {
              label: '여성',
              backgroundColor: 'rgba(255, 99, 132, 0.2)',
              borderColor: 'rgba(255, 99, 132, 1)',
              pointBackgroundColor: 'rgba(255, 99, 132, 1)',
              data: [55, 65, 85, 70, 50]
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