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
import Chart2 from "@/views/areaAnalytics/Chart2.vue";


// 1. /members/** : 회원 관련 활동 (로그인, 회원가입, 비밀번호 변경 등)
// 2. /community/** : 커뮤니티 관련 활동 (커뮤니티 CRUD)
// 3. /analytics/** : 상권 및 프랜차이즈 분석 관련 활동
const routes = [
  {
    path: "/members/login",
    name: "login",
    component: Login,
  },
  {
    path: "/members/signup",
    name: "signUp",
    component: SignUp,
  },
  {
    path: '/members/mypage',
    name: 'Mypage',
    component: Mypage
  },
  {
    path: '/members/password',
    name: 'PasswordRecovery',
    component: PasswordRecovery
  },
  {
    path: '/community',
    name: 'Community',
    component: Community
  },
  // const router = useRouter()
  // const boardId = router.params.boardId 로 접근
  {
    path: "/community/:boardId",
    name: "Community",
    component: Community,
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
    path: '/chart/:id',
    name: 'Chart',
    component: Chart2
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;
