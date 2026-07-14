<script setup lang="ts">

import { ref, onMounted } from "vue";
import api from "../api/api";


interface OrderResponse {
  id: number;
  userId: number;
  userName: string;
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


async function loadOrders() {

  loading.value = true;

  try {

    const response = await api.get( `/order/${status.value}`,
      {
        params: {
          page: 0
        }
      }
    );

    orders.value = response.data;

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


</script>


<template>

<div class="container">

    <h1>Orders</h1>


    <select
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
      Loading...
    </div>


    <table v-else>

      <thead>
        <tr>
          <th>ID</th>
          <th>User</th>
          <th>Status</th>
          <th>Price</th>
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
            {{ order.userName }}
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
            No orders found
          </td>

        </tr>


      </tbody>

    </table>

</div>

</template>