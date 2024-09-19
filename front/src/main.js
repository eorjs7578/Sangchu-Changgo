import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { createPinia } from 'pinia';
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';

const app = createApp(App);
// pinia 설정
const pinia = createPinia();

app.use(pinia);
app.use(router);
app.mount('#app');