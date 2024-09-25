<template>
  <div>
    <component :is="props.chartType" :chart-data="props.chartData" :chart-options="props.chartOptions" />
  </div>
</template>

<script setup>
import { defineProps } from "vue";
import { Bar, Line, Pie, Radar } from "vue-chartjs";
import LineChart from "@/components/charts/LineChart.js";

import {
  Title,
  Legend,
  Tooltip,
  ArcElement,
  BarElement,
  LineElement,
  PointElement,
  Chart as ChartJS,
  RadialLinearScale
} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, RadialLinearScale, PointElement, LineElement, BarElement, ArcElement);

const props = defineProps({
    chartType: {
      type: String,
      required: true, // 'bar', 'line', 'pie', 'radar', 'linechart' 중 하나
    },
    chartData: {
      type: Object,
      required: true,
    },
    chartOptions: {
      type: Object,
      default: () => ({}), // 기본 옵션을 빈 객체로 설정
    }
});
</script>

<style scoped>
/* 스타일을 여기에 추가하세요 */
</style>
