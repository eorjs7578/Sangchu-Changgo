import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'

import piniaPersistedState from "pinia-plugin-persistedstate"
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

const app = createApp(App)
const pinia = createPinia()
pinia.use(piniaPersistedState)
app.use(pinia)
app.use(router)

app.mount('#app')
