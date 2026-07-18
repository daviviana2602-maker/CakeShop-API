<script setup lang="ts">

import { ref } from "vue"
import { useRoute } from "vue-router"
import { editProduct } from "../service/productService"
import { showSuccess, showError } from "@/service/notificationService"


const route = useRoute()  


const id = Number(route.params.id)

const name = ref("")
const price = ref(0)


async function handleSubmit() {

  const data = {name: name.value, price: price.value}

  try {

    await editProduct(id, data)

    showSuccess("Produto atualizado!")

  } catch(error:any) {

    showError(error);

  }

}

</script>


<template>

<div class="edit-page">


  <div class="edit-card">


    <h1>
      Editar produto
    </h1>


    <form @submit.prevent="handleSubmit">


      <input
        v-model="name"
        placeholder="Novo nome para o produto"
      >


      <input
        v-model.number="price"
        type="number"
        step="0.01"
        placeholder="Novo preço"
      >


      <button type="submit">
        Salvar alterações
      </button>


    </form>


  </div>


</div>

</template>



<style scoped>

.edit-page {

  width: 100%;

  display: flex;

  justify-content: center;

  align-items: center;

}



.edit-card {

  width: 450px;

  background: white;

  padding: 35px;

  border-radius: 18px;

  border: 1px solid #eadbc8;

  box-shadow: 0 10px 25px rgba(107,66,38,.12);

}



h1 {

  text-align: center;

  color: #5c3b24;

  margin-bottom: 30px;

  font-size: 34px;

}



form {

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