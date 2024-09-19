<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h3>선택 상권 총 점포 수</h3>
        <div style="width: 100%; max-width: 600px; height: 400px">
          <Bar :data="chartData" :options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

export default {
  name: "MetricsChart",
  components: { Bar },
  data() {
    return {
      chartData: {
        labels: [
          "자동차용품",
          "인테리어",
          "가구/침구",
          "주방용품",
          "생활용품",
          "기타 용품",
        ],
        datasets: [
          {
            backgroundColor: "rgba(255, 99, 132, 0.8)",
            data: [90, 75, 70, 40, 65, 95],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        indexAxis: "y", // 가로 막대 차트
        plugins: {
          legend: {
            display: false, // 범례 숨기기
          },
        },
        scales: {
          x: {
            beginAtZero: true,
            max: 100, // 최대값 100
          },
        },
      },
    };
  },
};
</script>
