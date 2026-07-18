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

<div class="create-product-page">

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

</div>


</template>



<style scoped>

.create-product-page {

  width: 100%;

  display: flex;

  justify-content: center;

  align-items: center;

}



h1 {

  color: #5c3b24;

  font-size: 36px;

  margin-bottom: 30px;

  text-align: center;

}



form {

  width: 450px;

  background: white;

  padding: 35px;

  border-radius: 18px;

  border: 1px solid #eadbc8;

  box-shadow: 0 10px 25px rgba(107,66,38,.12);

  display: flex;

  flex-direction: column;

  gap: 18px;

}



input {

  padding: 14px;

  border-radius: 10px;

  border: 1px solid #d8c6b5;

  font-size: 16px;

  background: #fffdf9;

}



input:focus {

  outline: none;

  border-color: #556b2f;

  box-shadow: 0 0 0 4px rgba(85,107,47,.15);

}



button {

  padding: 14px;

  border: none;

  border-radius: 12px;

  background: #556b2f;

  color: white;

  font-size: 16px;

  font-weight: 600;

  cursor: pointer;

  transition: .25s;

}



button:hover {

  background: #465a27;

  transform: translateY(-2px);

}


</style>