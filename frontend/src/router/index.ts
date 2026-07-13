import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/homeView.vue'
import CreateAccountView from '../views/CreateAccountView.vue'


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
    }
  ]
})

export default router
