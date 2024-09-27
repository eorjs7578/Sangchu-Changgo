<template>
  <div class="overflow-auto">
    <Dashboard />

    <WeeklyVisitorChart />

    <HourlyVisitorChart />

    <QuarterlyVisitorChart />

    <AgeGroupChart />

    <StopAgeGroupChart />

    <MetricsChart />

    <TrendChart />

    <div class="mb-8">
      <HorizontalBarChart
        title="평일 평균 매출액은"
        :data="{ male: 15, female: 85 }"
      />
    </div>

    <div class="mb-8">
      <HorizontalBarChart
        title="평일 평균 매출건수는"
        :data="{ male: 20, female: 80 }"
      />
    </div>

    <div class="mb-8">
      <RadarChart title="연령별 매출액" />
    </div>

    <div class="container-fluid">
      <div class="card mb-3 shadow-sm">
        <h2 class="font-bold ms-1">평균 일대료는</h2>
        <p class="font-bold text-blue-300 ms-2">4,110,178원 입니다.</p>
      </div>
    </div>

    <div class="mb-8">
      <VerticalBarChart title="중앙값 평균 일대료" />
    </div>

    <div class="mb-8">
      <RadarChart title="상권 연령층" />
    </div>

    <div class="container-fluid">
      <div class="card mb-3 shadow-sm">
        <span class="text-xl font-bold mb-2" style="display: inline-block"
          >유동인구는</span
        ><b
          class="text-3xl font-bold text-blue-600"
          style="display: inline-block"
          >많은편 입니다.</b
        ><span class="text-xl font-bold mb-2" style="display: inline-block"
          >입니다</span
        >
      </div>
    </div>
  </div>
</template>

<script>
import Dashboard from "@/components/areaAnalytics/Dashboard.vue";
import WeeklyVisitorChart from "@/components/charts/WeeklyVisitorChart.vue";
import HourlyVisitorChart from "@/components/charts/HourlyVisitorChart.vue";
import QuarterlyVisitorChart from "@/components/charts/QuarterlyVisitorChart.vue";
import AgeGroupChart from "@/components/charts/AgeGroupChart.vue";
import BarChart from "@/components/charts/BarChart.vue";
import LineChart from "@/components/charts/LineChart.vue";
import StopAgeGroupChart from "@/components/charts/StopAgeGroupChart.vue";
import MetricsChart from "@/components/charts/MetricsChart.vue";
import TrendChart from "@/components/charts/TrendChart.vue";
import HorizontalBarChart from "@/components/charts/HorizontalBarChart.vue";
import RadarChart from "@/components/charts/RadarChart.vue";
import VerticalBarChart from "@/components/charts/VerticalBarChart.vue";

export default {
  name: "App",
  components: {
    Dashboard,
    WeeklyVisitorChart,
    HourlyVisitorChart,
    QuarterlyVisitorChart,
    AgeGroupChart,
    BarChart,
    LineChart,
    StopAgeGroupChart,
    MetricsChart,
    TrendChart,
    HorizontalBarChart,
    RadarChart,
    VerticalBarChart,
  },
};
</script>

<style>
/* Add any global styles here */
body {
  background-color: #f3f4f6;
}
span,
b {
  white-space: nowrap; /* 공백을 무시 */
}
</style>
