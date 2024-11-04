<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <!-- 차트 크기를 제한하기 위해 div에 width와 height 설정 -->
        <div style="max-width: 600px; height: 300px">
          <Radar :data="chartData" :options="chartOptions" />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineProps } from "vue";
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

const props = defineProps({
  labels: {
    type: Array,
    required: true,
  },
  data: {
    type: Array,
    required: true,
  },
  chartType: {
    type: String,
    required: true,
  }
});
  
  const chartData= {
    labels: props.labels,
    datasets: [
      {
        label: props.chartType,
        backgroundColor: "rgba(54, 162, 235, 0.2)",
        borderColor: "rgba(54, 162, 235, 1)",
        pointBackgroundColor: "rgba(54, 162, 235, 1)",
        data: props.data,
      },
    ],
  };
  const chartOptions ={
    responsive: true,
    maintainAspectRatio: false, // 부모 요소의 크기에 따라 반응형으로 동작
    plugins: {
      legend: {
        position: "top", // 범례를 차트 상단에 표시
      },
      tooltip: {
        callbacks: {
          label: function (tooltipItem) {
            let suffix = "";
            if (props.chartType === "연령별 매출") {
              suffix = "원"
            } else {
              suffix = "명"
            }
            const dataset = tooltipItem.dataset;
            const value = dataset.data[tooltipItem.dataIndex];
            return `${props.chartType}: ${value.toLocaleString()}${suffix}`;
          },
        },
      }
    },
    scales: {
      r: {
        beginAtZero: true, // 라디얼 스케일 시작 값을 0으로
      },
    },
  };


</script>
