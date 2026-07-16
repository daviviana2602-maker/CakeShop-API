<script setup lang="ts">

import { ref } from "vue"
import { useRoute } from "vue-router"
import { editProduct } from "../service/productService"


const route = useRoute()  


const id = Number(route.params.id)

const name = ref("")
const price = ref(0)


async function handleSubmit() {

  const data = {name: name.value, price: price.value}

  try {

    await editProduct(id, data)

    alert("Produto atualizado!")

  } catch(error:any) {

    alert(error.response.data.message)

  }

}

</script>


<template>

<h1>Editar produto</h1>

<form @submit.prevent="handleSubmit">

  <input
    v-model="name"
    placeholder="Nome"
  >

  <input
    v-model.number="price"
    type="number"
    step="0.01"
    placeholder="Preço"
  >

  <button type="submit">
    Editar
  </button>

</form>

</template>