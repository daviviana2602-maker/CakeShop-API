<script setup lang="ts">
import { ref } from "vue";

import AdminOrderView from "./AdminOrderView.vue";
import AdminSearchUserView from "./AdminSearchUserView.vue";

const page = ref("orders");
</script>

<template>

    <button @click="page = 'orders'">
        Pedidos
    </button>

    <button @click="page = 'users'">
        Usuários
    </button>

    <AdminOrderView v-if="page === 'orders'" />

    <AdminSearchUserView v-else />

</template>