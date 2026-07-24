<script setup lang="ts">

import { ref } from "vue"
import { useRoute, useRouter } from "vue-router"
import { showError, showSuccess } from "@/service/notificationService"

import {
  addItem,
  cancelOrder,
  concludeOrder
} from "../service/orderService"


const route = useRoute()
const router = useRouter()


const orderId = Number(route.params.id)


const productId = ref<number>(0)
const quantity = ref<number>(1)



async function handleAddItem() {


  try {


    await addItem(
      orderId,
      {
        productId: productId.value,
        quantity: quantity.value
      }
    )


    productId.value = 0
    quantity.value = 1



  } catch(error:any) {

    showError(error);

  }

}



async function handleCancel() {


  try {


    await cancelOrder(orderId)


    showSuccess("Pedido cancelado!")


    router.push("/")


  } catch(error:any) {

    showError(error);

  }


}



async function handleConclude() {


  try {


    await concludeOrder(orderId)


    showSuccess("Pedido concluído!")


    router.push("/")


  } catch(error:any) {

    showError(error);

  }


}


</script>



<template>

<h1>
Carrinho #{{ orderId }}
</h1>



<section>

<h2>
Adicionar produto
</h2>


<input
v-model.number="productId"
type="number"
placeholder="ID Produto"
/>


<input
v-model.number="quantity"
type="number"
placeholder="Quantidade"
/>


<button @click="handleAddItem">
Adicionar
</button>


</section>



<button @click="handleCancel">
Cancelar
</button>


<button @click="handleConclude">
Finalizar compra
</button>


</template>