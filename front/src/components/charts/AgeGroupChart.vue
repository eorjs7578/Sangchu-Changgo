<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h3>연령별 유동인구</h3>
        <!-- 차트 크기를 제한하기 위해 div에 width와 height 설정 -->
        <div style="max-width: 600px; height: 300px">
          <Radar :data="chartData" :options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Radar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  RadialLinearScale,
  PointElement,
  LineElement,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  RadialLinearScale,
  PointElement,
  LineElement
);

export default {
  name: "AgeGroupChart",
  components: { Radar },
  data() {
    return {
      chartData: {
        labels: ["20대", "30대", "40대", "50대", "60대 이상"],
        datasets: [
          {
            label: "남성",
            backgroundColor: "rgba(54, 162, 235, 0.2)",
            borderColor: "rgba(54, 162, 235, 1)",
            pointBackgroundColor: "rgba(54, 162, 235, 1)",
            data: [65, 75, 70, 80, 60],
          },
          {
            label: "여성",
            backgroundColor: "rgba(255, 99, 132, 0.2)",
            borderColor: "rgba(255, 99, 132, 1)",
            pointBackgroundColor: "rgba(255, 99, 132, 1)",
            data: [55, 65, 85, 70, 50],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false, // 부모 요소의 크기에 따라 반응형으로 동작
        plugins: {
          legend: {
            position: "top", // 범례를 차트 상단에 표시
          },
        },
        scales: {
          r: {
            beginAtZero: true, // 라디얼 스케일 시작 값을 0으로
          },
        },
      },
    };
  },
};
</script>
