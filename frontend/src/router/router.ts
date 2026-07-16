import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/homeView.vue'
import createAccountView from '../views/createAccountView.vue'
import loginView from '../views/loginView.vue'
import createProductView from '../views/createProductView.vue'
import editProductView from '../views/editProductView.vue'
import listProductsView from '../views/listProductsView.vue'
import updateProfileView from '../views/updateProfileView.vue'
import OrderView from '../views/orderView.vue'
import AdminView from '../views/adminView.vue'


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
      component: listProductsView
    },
    {
      path: '/update-profile',
      component: updateProfileView
    },
    {
      path: '/order/:id',
      component: OrderView
    },
    {
      path: '/admin',
      component: AdminView,
      meta: {
        requiresAdmin: true
      }
    }
  ]

})


router.beforeEach((to) => {

  const role = localStorage.getItem("role")

  if (to.meta.requiresAdmin && role !== "ADMIN") {
    return "/"
  }

})


export default router