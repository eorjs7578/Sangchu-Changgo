// components/LineChart.js
import { defineComponent, h } from 'vue'; // defineComponent와 h 함수를 사용
import { Line } from 'vue-chartjs';
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
} from 'chart.js';

// Chart.js에 필요한 구성 요소 등록
ChartJS.register(Title, Tooltip, Legend, LineElement, CategoryScale, LinearScale, PointElement);

export default defineComponent({
  name: 'LineChart',
  props: {
    chartData: {
      type: Object,
      required: true,
    },
    options: {
      type: Object,
      required: true,
    },
  },
  setup(props) {
    return () => 
      h(Line, {
        data: props.chartData, // chartData를 data로 전달
        options: props.options, // options도 동일하게 전달
      });
  },
});
