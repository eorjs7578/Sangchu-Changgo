<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h3 class="card-title">요일별 유동인구</h3>
        <br />
        <div style="max-width: 100%; height: 300px">
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
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale
);

export default {
  name: "WeeklyVisitorChart",
  components: { Bar },
  data() {
    return {
      chartData: {
        labels: ["월", "화", "수", "목", "금", "토", "일"],
        datasets: [
          {
            label: "유동인구",
            backgroundColor: "#3B82F6",
            data: [
              3000000, 3500000, 3200000, 3800000, 4500000, 4000000, 3700000,
            ],
          },
        ],
      },
      chartOptions: {
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
                    new Intl.NumberFormat("ko-KR").format(context.parsed.y) +
                    "명";
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
      },
    };
  },
};
</script>

<style scoped>
/* 필요한 경우 여기에 추가 스타일을 작성 */
</style>
