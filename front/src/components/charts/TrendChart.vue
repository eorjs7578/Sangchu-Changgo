<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h3>추정 매출액</h3>
        <div style="max-width: 600px; height: 300px">
          <Line :data="chartData" :options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Line } from "vue-chartjs";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Filler,
  Legend,
} from "chart.js";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Filler,
  Legend
);

export default {
  name: "TrendChart",
  components: { Line },
  data() {
    return {
      chartData: {
        labels: ["2023.1월", "2023.2월", "2023.3월", "2023.4월", "2023.5월"],
        datasets: [
          {
            label: "매출액",
            backgroundColor: "rgba(255, 99, 132, 0.2)",
            borderColor: "rgba(255, 99, 132, 1)",
            data: [65, 30, 25, 60, 45],
            fill: true, // 배경색 채우기
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false, // 범례 숨기기
          },
        },
        scales: {
          y: {
            beginAtZero: true, // Y축 0부터 시작
          },
        },
      },
    };
  },
};
</script>
