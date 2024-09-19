<template>
    <div>
      <h2>시간대별 유동인구</h2>
      <HourlyVisitorChart />
      
      <h2>분기별 평균 유동인구</h2>
      <QuarterlyVisitorChart />
      
      <h2>연령별 유동인구</h2>
      <AgeGroupChart />
    </div>
  </template>
  
  <script>
  import HourlyVisitorChart from '@/components/charts/HourlyVisitorChart.vue'
  import QuarterlyVisitorChart from '@/components/charts/QuarterlyVisitorChart.vue'
  import AgeGroupChart from '@/components/charts/AgeGroupChart.vue'
  
  export default {
    name: 'App',
    components: {
      HourlyVisitorChart,
      QuarterlyVisitorChart,
      AgeGroupChart
    }
  }
  </script>