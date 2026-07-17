import { createRouter, createWebHistory } from 'vue-router'
import createAccountView from '../views/createAccountView.vue'
import loginView from '../views/loginView.vue'
import createProductView from '../views/createProductView.vue'
import editProductView from '../views/editProductView.vue'
import updateProfileView from '../views/updateProfileView.vue'
import OrderView from '../views/orderView.vue'
import AdminView from '../views/adminView.vue'
import HomeProductsView from '@/views/homeProductsView.vue'
import AppLayout from '@/Layouts/AppLayout.vue'


const router = createRouter({

  history: createWebHistory(import.meta.env.BASE_URL),

  routes: [

  {
  path: "/",
  component: AppLayout,
  children: [
    {
      path: '',
      component: HomeProductsView,
      meta: {
        requiresToken: true
      }
    },
    {
      path: 'update-profile',
      component: updateProfileView,
      meta: {
        requiresToken: true
      }
    },
      {
      path: 'admin',
      component: AdminView,
      meta: {
        requiresAdmin: true,
        requiresToken: true
      }
    }
  ]
},

    {
      path: '/create-account',
      component: createAccountView,
    },
    {
      path: '/login',
      component: loginView
    },
    {
      path: '/create-product',
      component: createProductView,
      meta: {
        requiresAdmin: true
      }
    },
    {
      path: '/edit-product/:id',
      component: editProductView,
      meta: {
        requiresAdmin: true
      }
    },
    {
      path: '/order/:id',
      component: OrderView,
      meta: {
        requiresToken: true
      }
    },
  ]

})


router.beforeEach((to) => {

  const role = localStorage.getItem("role")

  if (to.meta.requiresAdmin && role !== "ADMIN") {
    return "/"
  }

})


router.beforeEach((to) => {

  const token = localStorage.getItem("accessToken")

  if (to.meta.requiresToken && token == null) {
    return "/login"
  }

})


export default router