import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/homeView.vue'
import createAccountView from '../views/createAccountView.vue'
import loginView from '../views/loginView.vue'
import createProductView from '../views/createProductView.vue'
import editProductView from '../views/editProductView.vue'
import ListProductsView from '../views/listProductsView.vue'


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
    },
    {
      path: '/edit-product/:id',
      component: editProductView
    },
    {
     path: '/products',
      component: ListProductsView
    }
  ]
})

export default router