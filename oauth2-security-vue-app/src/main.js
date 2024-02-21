import './assets/main.css'

import {createApp} from 'vue'
import App from './App.vue'
import router from './router/router.js' // Add this line

createApp(App).use(router).mount('#app')
