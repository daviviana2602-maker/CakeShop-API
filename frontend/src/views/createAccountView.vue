<script setup lang="ts">

import { ref } from "vue"
import { createAccount } from "../service/authService"
import { showSuccess, showError } from "@/service/notificationService"
import { handleApiError } from "@/errorControl/handleApiError"


const name = ref("")
const email = ref("")
const password = ref("")


async function handleSubmit() {
  

  const data = {
    name: name.value,
    email: email.value,
    password: password.value
  }

  try {

        await createAccount(data)

        showSuccess("Conta criada!");

    } catch(error:any){

        showError(handleApiError(error));

    }

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