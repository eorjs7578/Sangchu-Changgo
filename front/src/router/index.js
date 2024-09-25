import { createRouter, createWebHistory } from 'vue-router';
import Login from '@/views/accounts/Login.vue';
import SignUp from '@/views/accounts/SignUp.vue';
import PasswordRecovery from '@/views/accounts/PasswordRecovery.vue';
import Community from '@/views/Community.vue';
import Franchise from '@/views/Franchise.vue';
import InterestAreas from '@/views/InterestAreas.vue';
import MarketAnalysis from '@/views/MarketAnalysis.vue';
import AreaAnalytics from '@/views/areaAnalytics/AreaAnalytics.vue';
import Chart from '@/views/areaAnalytics/Chart.vue';
import Mypage from '@/views/accounts/Mypage.vue';

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signup',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/mypage',
    name: 'Mypage',
    component: Mypage
  },
  {
    path: '/passwordrecovery',
    name: 'PasswordRecovery',
    component: PasswordRecovery
  },
  {
    path: '/community',
    name: 'Community',
    component: Community
  },
  {
    path: '/franchise',
    name: 'Franchise',
    component: Franchise
  },
  {
    path: '/interestareas',
    name: 'InterestAreas',
    component: InterestAreas
  },
  {
    path: '/marketanalysis',
    name: 'MarketAnalysis',
    component: MarketAnalysis
  },
  {
    path: '/areaAnalytics',
    name: 'AreaAnalytics',
    component: AreaAnalytics
  },
  {
    path: '/chart',
    name: 'Chart',
    component: Chart
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
