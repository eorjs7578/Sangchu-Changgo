import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'

import piniaPersistedState from "pinia-plugin-persistedstate"
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { useAccountStore } from "@/stores/useAccountStore.js";

const app = createApp(App)
const pinia = createPinia()

pinia.use(piniaPersistedState)
app.use(pinia)
app.use(router)


// Vue 인스턴스와 연결하기 전에 전역 가드 설정
router.beforeEach((to, from, next) => {
  // store는 Pinia가 생성된 후에 사용할 수 있음
  const store = useAccountStore();

  if (to.path === "/") {
    next("/marketanalysis");
  } else if (to.matched.some(record => record.meta.requiresAuth)) {
    // 로그인 필요한 페이지에 접근하려 할 때, 로그인 여부 확인
    if (!store.isAuthenticated) {
      next({ name: 'login' }); // 로그인 페이지로 리디렉션
    } else {
      next(); // 인증되어 있으면 그대로 진행
    }
  } else {
    next(); // 인증 필요 없는 페이지는 그대로 진행
  }
});

app.mount('#app')
