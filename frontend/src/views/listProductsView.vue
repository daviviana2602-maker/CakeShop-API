<script setup lang="ts">

import { onMounted, ref } from "vue"
import { listProducts } from "../service/productService"
import { useRouter } from "vue-router"
import { deleteProduct } from "../service/productService"


const products = ref<any[]>([])

const page = ref(0);
const totalPages = ref(0);


async function loadProducts() {

  try {

    const response = await listProducts(page.value)

    products.value = response.products;
    totalPages.value = response.totalPages;

  } catch(error:any) {

    alert(error.response.data.message)

  }

}


onMounted(() => {
  loadProducts()
})


async function handleDelete(id: number) {

  try {

    await deleteProduct(id)

    alert("Produto deletado!")

    loadProducts()

  } catch(error:any) {

    alert(error.response.data.message)

  }

}


const router = useRouter()  // change to another route

function goToEdit(id: number) {
  router.push(`/edit-product/${id}`)
}


function nextPage() {

  page.value++;
  loadProducts();

}


function previousPage() {

  page.value--;
  loadProducts();

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


<button
  @click="previousPage"
  :disabled="page === 0"
>
  Anterior
</button>


<button
  @click="nextPage"
  :disabled="page + 1 >= totalPages"
>
  Próxima
</button>

</template>