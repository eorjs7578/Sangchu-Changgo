<template>
  <div class="container-fluid">
    <div class="card mb-3 shadow-sm">
      <div class="card-body">
        <Bar :data="chartData" :options="chartOptions" />
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
  name: "VerticalBarChart",
  components: { Bar },
  props: {
    title: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      chartData: {
        labels: ["지난 1월", "1월", "2월", "3월"],
        datasets: [
          {
            backgroundColor: "rgba(54, 162, 235, 0.8)",
            data: [3000, 3500, 3300, 3100],
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false,
          },
          title: {
            display: true,
            text: this.title,
          },
        },
        scales: {
          y: {
            beginAtZero: true,
          },
        },
      },
    };
  },
};
</script>
