<script setup lang="ts">

import { ref } from "vue"
import { login } from "../service/authService"


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


        alert("Login realizado!")


    } catch(error:any){

        alert(error.response.data.message)

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