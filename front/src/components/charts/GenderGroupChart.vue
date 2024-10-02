<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <!-- 차트 크기를 제한하기 위해 div에 width와 height 설정 -->
        <div style="max-width: 600px; height: 300px">
          <Pie :data="chartData" :options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { Pie } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  ArcElement,
} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, ArcElement);

export default {
  name: "GenderGroupChart",
  components: { Pie },
  props: {
    genderData: {
      type: Object,
      required: true,
    },
  },
  computed: {
    chartData() {
      return {
        labels: this.genderData.labels, // genderData의 labels 사용
        datasets: [
          {
            label: "성별 인구 수",
            backgroundColor: ["rgba(54, 162, 235, 0.2)", "rgba(255, 99, 132, 0.2)"],
            borderColor: ["rgba(54, 162, 235, 1)", "rgba(255, 99, 132, 1)"],
            data: this.genderData.data, // genderData의 data 사용
          },
        ],
      };
    },
    chartOptions() {
      return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: "top",
          },
          tooltip: {
            callbacks: {
              label: function (tooltipItem) {
                const dataset = tooltipItem.dataset;
                const value = dataset.data[tooltipItem.dataIndex];
                return `${tooltipItem.label}: ${value.toLocaleString()}`;
              },
            },
          },
        },
      };
    },
  },
};
</script>
