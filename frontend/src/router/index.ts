import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/homeView.vue'
import createAccountView from '../views/createAccountView.vue'
import loginView from '../views/loginView.vue'
import createProductView from '../views/createProductView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [
    {
      path: '/',
      component: HomeView
    },
    {
      path: '/create-account',
      component: createAccountView
    },
    {
      path: '/login',
      component: loginView
    },
    {
      path: '/create-product',
      component: createProductView
    }
  ]
})

export default router