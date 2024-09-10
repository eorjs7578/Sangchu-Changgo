import { createRouter, createWebHistory } from 'vue-router';
import Login from '@/views/Login.vue';
import SignUp from '@/views/SignUp.vue';
import Community from '@/views/Community.vue';
import Franchise from '@/views/Franchise.vue';
import InterestAreas from '@/views/InterestAreas.vue';
import MarketAnalysis from '@/views/MarketAnalysis.vue';

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
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
