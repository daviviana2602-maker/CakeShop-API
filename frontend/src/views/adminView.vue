<script setup lang="ts">

import { ref } from "vue";

import AdminOrderView from "./AdminOrderView.vue";
import AdminSearchUserView from "./AdminSearchUserView.vue";
import CreateProductView from "./createProductView.vue";


const page = ref("orders");


</script>




<template>

    <button @click="page = 'orders'">
        Pedidos
    </button>

    <button @click="page = 'users'">
        Usuários
    </button>

    <button @click="page = 'products'">
      Criar produto
    </button>

       <AdminOrderView
      v-if="page === 'orders'"
    />


    <AdminSearchUserView
      v-else-if="page === 'users'"
    />


    <CreateProductView
      v-else
    />

</template>



<style scoped>

.admin-page {

  width: 100%;

  display: flex;

  flex-direction: column;

  align-items: center;

}


.menu {

  display: flex;

  gap: 15px;

  margin-bottom: 35px;

}


button {

  padding: 12px 28px;

  border: none;

  border-radius: 25px;

  background: #556b2f;

  color: white;

  font-size: 15px;

  font-weight: 600;

  cursor: pointer;

  transition: .25s;

}


button:hover {

  background: #465a27;

  transform: translateY(-2px);

}


button:focus {

  outline: none;

}


.content {

  width: 100%;

  max-width: 900px;

}


</style>