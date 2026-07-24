<script setup lang="ts">

import { ref } from "vue"
import { login } from "../service/authService"
import { showSuccess, showError } from "@/service/notificationService"
import { useRouter } from "vue-router"


const email = ref("")
const password = ref("")

const router = useRouter()


function go(path: string) {

  router.push(path)

}



async function handleLogin() {
  

  const data = {
    email: email.value,
    password: password.value
  }

  try {

        const response = await login(data)

        localStorage.setItem("accessToken", response.accessToken)
        localStorage.setItem("role", response.role)


        showSuccess("Login realizado!")                              
                                     

        go("/")


    } catch(error:any){

        showError(error);

    }

}

</script>



<template>

  <div class="container">

    <div class="blob blob-1"></div>

    <div class="blob blob-2"></div>

    <div class="login-card">

      <h1>🍰 CakeShop</h1>

      <form @submit.prevent="handleLogin">

        <input
          v-model="email"
          placeholder="Email"
        >

        <input
          v-model="password"
          type="password"
          placeholder="Senha"
        >

        <button type="submit">
        Entrar
        </button>

      <button @click="go('/create-account')">
      Criar Conta
      </button>

      </form>

    </div>

  </div>

</template>



<style scoped>

* {

  box-sizing: border-box;

}

.container {

  min-height: 100vh;

  display: flex;

  justify-content: center;

  align-items: center;

  background: linear-gradient(135deg, #fff8ed, #f8e9d4);

  position: relative;

  overflow: hidden;

}

.blob {

  position: absolute;

  border-radius: 50%;

  filter: blur(90px);

  opacity: .45;

  animation: float 14s ease-in-out infinite;

}

.blob-1 {

  width: 320px;

  height: 320px;

  background: #7da453;

  top: -80px;

  left: -80px;

}

.blob-2 {

  width: 380px;

  height: 380px;

  background: #b57a47;

  bottom: -120px;

  right: -120px;

  animation-delay: 4s;

}

.login-card {

  position: relative;

  z-index: 1;

  width: 420px;

  background: rgba(255, 255, 255, .92);

  backdrop-filter: blur(10px);

  padding: 40px;

  border-radius: 18px;

  box-shadow: 0 12px 30px rgba(0, 0, 0, .12);

  display: flex;

  flex-direction: column;

}

h1 {

  text-align: center;

  color: #5c3b24;

  margin-bottom: 30px;

  font-size: 36px;

}

form {

  display: flex;

  flex-direction: column;

  gap: 18px;

}

input {

  padding: 14px;

  border: 1px solid #d8c6b5;

  border-radius: 10px;

  font-size: 16px;

  transition: .2s;

}

input:focus {

  outline: none;

  border-color: #556b2f;

  box-shadow: 0 0 0 4px rgba(85, 107, 47, .15);

}

button {

  margin-top: 10px;

  padding: 14px;

  border: none;

  border-radius: 10px;

  background: #556b2f;

  color: white;

  font-size: 16px;

  font-weight: bold;

  cursor: pointer;

  transition: .25s;

}

button:hover {

  background: #465a27;

  transform: translateY(-2px);

}

button:active {

  transform: translateY(0);

}

@keyframes float {

  0% {

    transform: translate(0, 0);

  }

  25% {

    transform: translate(25px, -20px);

  }

  50% {

    transform: translate(-20px, 20px);

  }

  75% {

    transform: translate(20px, 10px);

  }

  100% {

    transform: translate(0, 0);

  }

}

</style>