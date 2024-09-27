<!-- 이 문제는 vue-chartjs에서 최신 버전에서는 chart-data와 chart-options라는 props 대신, data와 options를 사용해야 합니다.
  따라서 LineChart에 전달하는 props의 이름을 :chart-data와 :chart-options에서 :data와 :options로 변경해야 합니다. -->
<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <br />
        <div style="max-width: 100%; height: 200px">
          <Line :data="chartData" :options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { Line } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale,
} from "chart.js";

// Chart.js에 필요한 구성 요소 등록
ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale
);

const props = defineProps({
  labels: {
    type: Array,
    required: true,
  },
  data: {
    type: Array,
    required: true,
  },
});

const chartData = {
  labels: props.labels,
  datasets: [
    {
      label: "시간대 별 유동인구",
      backgroundColor: "#36A2EB",
      borderColor: "#36A2EB",
      pointBackgroundColor: "#36A2EB",
      pointBorderColor: "#fff",
      data: props.data,
      borderWidth: 2,
      tension: 0.1, // 부드러운 선을 만들기 위한 옵션
      fill: false,
    },
  ],
}

const chartOptions = {
  responsive: true,
      maintainAspectRatio: true, // 비율을 유지하도록 설정
      scales: {
    x: {
      title: {
        display: true,
            text: "시간대",
      },
    },
    y: {
      beginAtZero: true,
          title: {
        display: true,
            text: "방문자 수",
      },
    },
  },
  plugins: {
    legend: {
      display: true,
          position: "top",
    },
    tooltip: {
      enabled: true,
    },
  },
}
</script>
