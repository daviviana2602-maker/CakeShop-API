<script setup lang="ts">

import { ref } from "vue"
import { createProduct } from "../service/productService"
import { showSuccess, showError } from "@/service/notificationService"


const name = ref("")
const price = ref(0)


async function handleSubmit() {

  const data = {
    name: name.value,
    price: price.value
  }

  try {

    await createProduct(data)

    showSuccess("Produto criado!")

    name.value = ""
    price.value = 0

  } catch(error:any) {

    showError(error);

  }

}

</script>


<template>

<h1>Criar produto</h1>

<form @submit.prevent="handleSubmit">

  <input 
    v-model="name"
    placeholder="Nome"
  >

  <input 
    v-model="price"
    type="number"
    step="0.01"
    placeholder="Preço"
  >

  <button type="submit">
    Criar
  </button>

</form>

</template>