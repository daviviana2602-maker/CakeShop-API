<script setup lang="ts">

import { ref } from "vue"
import { login } from "../service/authService"
import { showSuccess, showError } from "@/service/notificationService"
import { handleApiError } from "@/errorControl/handleApiError"


const email = ref("")
const password = ref("")


async function handleLogin() {
  

  const data = {
    email: email.value,
    password: password.value
  }

  try {

        const response = await login(data)

        localStorage.setItem("accessToken", response.accessToken)
        localStorage.setItem("refreshToken", response.refreshToken)
        localStorage.setItem("role", response.role)


        showSuccess("Login realizado!")


    } catch(error:any){

        showError(error);

    }

}

</script>



<template>
  <h1>Login</h1>

  <form @submit.prevent="handleLogin">

    <input v-model="email" placeholder="Email">

    <input 
      v-model="password"
      placeholder="Senha"
      type="password"
    >

    <button type="submit">
      Fazer login
    </button>

  </form>
</template>