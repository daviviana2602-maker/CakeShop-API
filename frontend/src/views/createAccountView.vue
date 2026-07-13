<script setup lang="ts">

import { ref } from "vue"
import { createAccount } from "../service/authService"


const name = ref("")
const email = ref("")
const password = ref("")


async function handleSubmit() {

  console.log("ENTROU NO SUBMIT")

  const data = {
    name: name.value,
    email: email.value,
    password: password.value
  }

  const response = await createAccount(data)

  console.log(response)
}

</script>



<template>
  <h1>Criar conta</h1>

  <form @submit.prevent="handleSubmit">

    <input v-model="name" placeholder="Nome">

    <input v-model="email" placeholder="Email">

    <input 
      v-model="password"
      placeholder="Senha"
      type="password"
    >

    <button type="submit">
      Criar conta
    </button>

  </form>
</template>