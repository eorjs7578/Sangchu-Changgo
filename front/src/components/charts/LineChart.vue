<template>
  <div>
    <!-- LineChart 컴포넌트에 chartData와 options를 props로 넘겨줌 -->
    <LineChart :data="chartData" :options="options" />
  </div>
</template>

<script>
import LineChart from '@/components/charts/LineChart'; // LineChart 컴포넌트 불러오기

export default {
  name: 'ParentComponent',
  components: {
    LineChart,
  },
  data() {
    return {
      // chartData를 설정
      chartData: {
        labels: ['4 / 8', '4 / 9', '4 / 10', '4 / 11', '4 / 12', '4 / 13', '4 / 14', '4 / 15', '4 / 16', '4 / 17'],
        datasets: [
          {
            label: '총 수면 시간',
            backgroundColor: 'rgba(255, 255, 255, 0)',
            borderColor: '#64CCA2',
            pointBackgroundColor: '#64CCA2',
            pointBorderColor: '#64CCA2',
            pointBorderWidth: 1,
            data: [45, 50, 60, 50, 45, 80, 50, 40, 68, 70],
            borderWidth: 2,
            tension: 0.1,
            fill: true,
          },
        ],
      },
      // options 설정
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          xAxes: [
            {
              stacked: false,
              display: true,
              grid: {
                drawBorder: false,
                color: '#323232',
                lineWidth: 1,
              },
              ticks: {
                beginAtZero: true,
                padding: 0,
              },
              scaleLabel: {
                display: true,
                labelString: '날짜',
              },
            },
          ],
          yAxes: [
            {
              stacked: false,
              display: true,
              ticks: {
                stepSize: 20,
                beginAtZero: true,
                max: 100,
                min: 0,
                padding: 10,
              },
              grid: {
                drawBorder: false,
                color: '#323232',
                lineWidth: 1,
              },
            },
          ],
        },
      },
    };
  },
};
</script>
