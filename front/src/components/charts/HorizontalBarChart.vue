<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <div style="max-width: 600px; height: 140px">
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

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale);

export default {
  name: "HorizontalBarChart",
  components: { Bar },
  props: {
    labels: {
      type: Array,
      required: true,
    },
    data: {
      type: Array,
      required: true,
    },
  },
  computed: {
    // 데이터를 퍼센트로 변환하여 chartData에 적용
    chartData() {
      // data 배열의 총합 계산
      const total = this.data.reduce((sum, value) => sum + value, 0);

      // 각 데이터를 퍼센트로 변환
      const percentageData = this.data.map(value => ((value / total) * 100).toFixed(2));

      return {
        labels: this.labels,
        datasets: [
          {
            backgroundColor: [
              "rgba(54, 162, 235, 0.8)",
              "rgba(255, 99, 132, 0.8)",
            ],
            data: percentageData,  // 퍼센트로 변환된 데이터 적용
          },
        ],
      };
    },
  },
  data() {
    return {
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        indexAxis: "y",  // 수평 막대 그래프
        plugins: {
          legend: {
            display: false,
          },
          title: {
            display: true,
          },
          tooltip: {
            callbacks: {
              label: function(context) {
                // 툴팁에서 퍼센트값을 표시
                let label = context.dataset.label || '';
                if (label) {
                  label += ': ';
                }
                if (context.parsed.x !== null) {
                  label += context.parsed.x + '%';
                }
                return label;
              }
            }
          }
        },
        scales: {
          x: {
            beginAtZero: true,
            max: 100,  // 퍼센트이므로 최대값을 100으로 설정
            ticks: {
              callback: function(value) {
                return value + '%';  // x축에 퍼센트 표시
              },
            },
          },
        },
      },
    };
  },
};
</script>
