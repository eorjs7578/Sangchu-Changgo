<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <br />
        <div style="max-width: 100%; height: 300px">
          <Bar :data="chartData" :options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

// Chart.js 모듈 등록
ChartJS.register(
    Title,
    Tooltip,
    Legend,
    BarElement,
    CategoryScale,
    LinearScale
);

// props 정의
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

// 차트 데이터 및 옵션 설정
const chartData = {
  labels: props.labels,
  datasets: [
    {
      label: "유동인구",
      backgroundColor: "#3B82F6",
      data: props.data,
    },
  ],
};

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false, // 비율을 유지하지 않음
  plugins: {
    legend: {
      display: false,
    },
    tooltip: {
      callbacks: {
        label: function (context) {
          let label = context.dataset.label || "";
          if (label) {
            label += ": ";
          }
          if (context.parsed.y !== null) {
            label +=
                new Intl.NumberFormat("ko-KR").format(context.parsed.y) + "명";
          }
          return label;
        },
      },
    },
  },
  scales: {
    y: {
      beginAtZero: true,
      ticks: {
        callback: function (value) {
          return new Intl.NumberFormat("ko-KR", {
            notation: "compact",
            compactDisplay: "short",
          }).format(value);
        },
      },
    },
  },
};
</script>

<style scoped>
/* 필요한 경우 여기에 추가 스타일을 작성 */
</style>
