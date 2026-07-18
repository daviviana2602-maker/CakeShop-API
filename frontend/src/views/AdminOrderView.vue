<script setup lang="ts">

import { ref, onMounted } from "vue";
import api from "../api/api";
import { showError } from "@/service/notificationService";


interface OrderResponse {
  id: number;
  userId: number;
  name: string;
  email: string;
  status: string;
  price: number;
}


const orders = ref<OrderResponse[]>([]);

const status = ref("PENDING");

const loading = ref(false);


const statuses = [
  "PENDING",
  "CONCLUDED",
  "CANCELED"
];


const page = ref(0);
const totalPages = ref(0);


async function loadOrders() {

  loading.value = true;

  try {

    const response = await api.get(
      `/order/${status.value}`,
      {
        params: {
          page: page.value
        }
      }
    );

    orders.value = response.data.content;
    totalPages.value = response.data.totalPages;

  } catch(error:any) {

       showError(error);

  } finally {

    loading.value = false;

  }

}


function changeStatus() {
  loadOrders();
}


onMounted(() => {
  loadOrders();
});


function nextPage() {
  page.value++;
  loadOrders();
}

function previousPage() {

  if(page.value > 0) {
    page.value--;
    loadOrders();
  }
}


</script>


<template>

<div class="container">

    <h1>Pedidos</h1>


    <select
      class="status-select"
      v-model="status"
      @change="changeStatus"
    >

      <option
        v-for="item in statuses"
        :key="item"
        :value="item"
      >
        {{ item }}
      </option>

    </select>


    <div v-if="loading">
      Carregando...
    </div>


    <table v-else>

      <thead>
        <tr>
          <th>ID</th>
          <th>Usuário</th>
          <th>Email</th>
          <th>Status</th>
          <th>Preço total</th>
        </tr>
      </thead>


      <tbody>

        <tr
          v-for="order in orders"
          :key="order.id"
        >

          <td>
            {{ order.id }}
          </td>

          <td>
            {{ order.name }}
          </td>

          <td>
            {{ order.email }}
          </td>

          <td>
            {{ order.status }}
          </td>

          <td>
            R$ {{ order.price.toFixed(2) }}
          </td>

        </tr>


        <tr v-if="orders.length === 0">

          <td colspan="4">
            Nenhum pedido encontrado
          </td>

        </tr>

      </tbody>

    </table>

<button
  class="page-button"
  @click="previousPage"
  :disabled="page === 0"
>
  Anterior
</button>


<button
  class="page-button"
  @click="nextPage"
  :disabled="page + 1 >= totalPages"
>
  Próxima
</button>


</div>

</template>



<style scoped>

.container {

  width: 100%;

  display: flex;

  flex-direction: column;

  align-items: center;

  gap: 25px;

}


h1 {

  color: #5c3b24;

  font-size: 38px;

}



.status-select {

  width: 250px;

  padding: 13px;

  border-radius: 12px;

  border: 1px solid #d8c6b5;

  background: white;

  font-size: 16px;

  cursor: pointer;

}



.status-select:focus {

  outline: none;

  border-color: #556b2f;

}



table {

  width: 900px;

  max-width: 95%;

  background: white;

  border-collapse: collapse;

  border-radius: 18px;

  overflow: hidden;

  box-shadow: 0 10px 25px rgba(107,66,38,.12);

  border: 1px solid #eadbc8;

}



thead {

  background: #556b2f;

  color: white;

}



th {

  padding: 16px;

  text-align: left;

  font-size: 15px;

}



td {

  padding: 15px;

  border-bottom: 1px solid #eee3d6;

  color: #5c4a3d;

}



tbody tr {

  transition: .2s;

}



tbody tr:hover {

  background: #fff8ed;

}



tbody tr:last-child td {

  border-bottom: none;

}



td[colspan] {

  text-align: center;

  padding: 30px;

  color: #8a7765;

}



.page-button {

  padding: 12px 25px;

  border: none;

  border-radius: 25px;

  background: #556b2f;

  color: white;

  font-weight: 600;

  cursor: pointer;

  transition: .25s;

}



.page-button:hover:not(:disabled) {

  background: #465a27;

  transform: translateY(-2px);

}



.page-button:disabled {

  opacity: .5;

  cursor: not-allowed;

}



</style>