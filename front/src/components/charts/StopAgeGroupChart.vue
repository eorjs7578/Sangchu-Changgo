<template>
  <div class="container-fluid">
    <!-- 차트 크기를 제한하기 위해 div에 width와 height 설정 -->
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <h3>연령별 상주인구</h3>
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
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend,
} from "chart.js";

// Chart.js에 필요한 구성 요소 등록
ChartJS.register(
  RadialLinearScale,
  PointElement,
  LineElement,
  Filler,
  Tooltip,
  Legend
);

export default {
  name: "AgeGroupChart",
  components: { Radar },
  data() {
    return {
      // chartData 설정
      chartData: {
        labels: ["20대", "30대", "40대", "50대", "60대 이상"],
        datasets: [
          {
            label: "남성",
            backgroundColor: "rgba(54, 162, 235, 0.2)",
            borderColor: "rgba(54, 162, 235, 1)",
            pointBackgroundColor: "rgba(54, 162, 235, 1)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(54, 162, 235, 1)",
            data: [65, 59, 90, 81, 56],
          },
          {
            label: "여성",
            backgroundColor: "rgba(255, 99, 132, 0.2)",
            borderColor: "rgba(255, 99, 132, 1)",
            pointBackgroundColor: "rgba(255, 99, 132, 1)",
            pointBorderColor: "#fff",
            pointHoverBackgroundColor: "#fff",
            pointHoverBorderColor: "rgba(255, 99, 132, 1)",
            data: [28, 48, 40, 19, 96],
          },
        ],
      },
      // chartOptions 설정
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false, // 부모 요소의 비율을 유지하지 않음
        scales: {
          r: {
            angleLines: {
              display: false, // 방사형 차트의 각도 라인 숨김
            },
            suggestedMin: 0, // 최소값 설정
            suggestedMax: 100, // 최대값 설정
          },
        },
      },
    };
  },
};
</script>
