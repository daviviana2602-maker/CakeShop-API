import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/homeView.vue'
import CreateAccountView from '../views/createAccountView.vue'
import loginView from '../views/loginView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: '/',
      component: HomeView
    },
    {
      path: '/create-account',
      component: CreateAccountView
    },
    {
      path: '/login',
      component: loginView
    }
  ]
})

export default router
