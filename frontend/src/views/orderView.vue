<script setup lang="ts">

import { ref, onMounted } from "vue"
import { useRoute, useRouter } from "vue-router"
import { showError, showSuccess } from "@/service/notificationService"

import {
  addItem,
  listOrder,
  cancelOrder,
  concludeOrder
} from "../service/orderService"


const route = useRoute()
const router = useRouter()


const orderId = Number(route.params.id)


const productId = ref<number>(0)
const quantity = ref<number>(1)


const items = ref<any[]>([])


onMounted(loadCart)


async function loadCart() {

  try {

    const response = await listOrder(orderId)

    items.value = response

  } catch(error:any) {

    showError(error);

  }

}



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


    await loadCart()


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



<section>


<h2>
Itens
</h2>


<p v-if="items.length === 0">
Carrinho vazio
</p>



<div
v-for="item in items"
:key="item.id"
>


<p>
{{ item.product?.name ?? item.productName }}
</p>

<p>
{{ item.product?.unitPrice ?? item.unitPrice }}
</p>

<p>
{{ item.product?.fullPrice ?? item.fullPrice }}
</p>

<p>
Quantidade: {{ item.quantity }}
</p>


<hr>


</div>


</section>



<button @click="handleCancel">
Cancelar
</button>


<button @click="handleConclude">
Finalizar compra
</button>


</template>