<template>
  <div>
    <component
        :is="chartComponent"
        :chart-data="chartData"
        :chart-options="chartOptions"
    />
  </div>
</template>

<script setup>
import {defineProps, computed} from "vue";
import {Bar, Line, Pie, Radar} from "vue-chartjs";

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
    required: true, // 'bar', 'line', 'pie', 'radar' 중 하나
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

// chartType에 따라 적절한 차트 컴포넌트를 결정
const chartComponent = computed(() => {
  switch (props.chartType) {
    case 'bar':
      return Bar;
    case 'line':
      return Line;
    case 'pie':
      return Pie;
    case 'radar':
      return Radar;
    default:
      return null; // 잘못된 타입 처리
  }
});

</script>

<style scoped>
/* 스타일을 여기에 추가하세요 */
</style>
