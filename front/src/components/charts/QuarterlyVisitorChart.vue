<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h3>분기별 평균 유동인구</h3>
        <div style="width: 100%; height: 400px;">
          <!-- chartData가 정의되었을 때만 차트를 렌더링 -->
          <Bar v-if="chartData && chartData.labels.length && chartData.datasets.length" :data="chartData" :options="chartOptions" />
          <!-- 데이터가 없을 때 보여줄 메시지 -->
          <p v-else>데이터 로드 중...</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Bar } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js';
import { ref, onMounted } from 'vue';

// Chart.js의 구성 요소들을 등록
ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

export default {
  name: 'QuarterlyVisitorChart',
  components: { Bar },
  setup() {
    const chartData = ref({
      labels: ['Loading...'],
      datasets: [
        {
          label: 'Loading Data',
          backgroundColor: '#e0e0e0',
          data: [0],
        },
      ],
    });

    const chartOptions = {
      responsive: true,
      maintainAspectRatio: false,
    };

    onMounted(() => {
      setTimeout(() => {
        chartData.value = {
          labels: ['2023 - 1분기', '2023 - 2분기', '2023 - 3분기', '2023 - 4분기'],
          datasets: [
            {
              label: 'Quarterly Visitors',
              backgroundColor: '#4C6EF5',
              data: [3000, 3500, 3300, 3100],
            },
            {
              label: 'Quarterly Revenue',
              backgroundColor: '#FF6F61',
              data: [12000, 14500, 13000, 12500],
            },
          ],
        };
      }, 1000); // 1초 후에 데이터를 설정
    });

    return { chartData, chartOptions };
  },
};
</script>
