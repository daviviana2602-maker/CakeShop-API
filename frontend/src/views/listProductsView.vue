<script setup lang="ts">

import { onMounted, ref } from "vue"
import { listProducts } from "../service/productService"
import { useRouter } from "vue-router"
import { deleteProduct } from "../service/productService"


const products = ref<any[]>([])

async function loadProducts() {

  try {

    products.value = await listProducts()

  } catch(error:any) {

    alert(error.response.data.message)

  }

}

onMounted(() => {
  loadProducts()
})




const router = useRouter()  // change to another route

function goToEdit(id: number) {
  router.push(`/edit-product/${id}`)
}



async function handleDelete(id: number) {

  try {

    await deleteProduct(id)

    alert("Produto deletado!")

    loadProducts()

  } catch(error:any) {

    alert(error.response.data.message)

  }

}

</script>



<template>

<h1>Produtos</h1>

<div v-for="product in products" :key="product.id">

  <h3>{{ product.name }}</h3>

  <p>R$ {{ product.price }}</p>

  <button @click="goToEdit(product.id)">
    Editar
  </button>  

  <button @click="handleDelete(product.id)">
    Excluir
  </button>

</div>

</template>