import {createRouter, createWebHistory} from 'vue-router'
import LoginRedirect from '@/components/loginRedirect.vue'
import LoginForm from '@/components/loginForm.vue'

const routes = [
    {
        path: '/login/redirect',
        name: 'LoginRedirect',
        component: LoginRedirect
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginForm
    }
    // other routes...
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router