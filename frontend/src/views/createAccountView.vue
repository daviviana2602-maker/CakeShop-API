<script setup lang="ts">

import { ref } from "vue"
import { createAccount } from "../service/authService"
import { showSuccess, showError } from "@/service/notificationService"
import { useRouter } from "vue-router"


const name = ref("")
const email = ref("")
const password = ref("")

const router = useRouter()

function go(path: string) {

  router.push(path)

}


async function handleSubmit() {
  

  const data = {
    name: name.value,
    email: email.value,
    password: password.value
  }

  try {

        await createAccount(data)

        showSuccess("Conta criada!");

        go("/login")

    } catch(error:any){

        showError(error);

    }

}

</script>



<template>

  <div class="container">

    <div class="blob blob-1"></div>

    <div class="blob blob-2"></div>


    <div class="register-card">

      <h1>
        🍰 Criar conta
      </h1>


      <form @submit.prevent="handleSubmit">

        <input
          v-model="name"
          placeholder="Nome"
        >


        <input
          v-model="email"
          placeholder="Email"
        >


        <input
          v-model="password"
          placeholder="Senha"
          type="password"
        >


        <button type="submit">
          Criar conta
        </button>


        <button
          type="button"
          class="back-button"
          @click="go('/login')"
        >
          Já tenho conta
        </button>


      </form>


    </div>


  </div>

</template>



<style scoped>

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


.register-card {

  width: 420px;

  background: rgba(255,255,255,.92);

  backdrop-filter: blur(10px);

  padding: 40px;

  border-radius: 18px;

  box-shadow: 0 12px 30px rgba(0,0,0,.12);

  display: flex;

  flex-direction: column;

  position: relative;

  z-index: 1;

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

  border: 1px solid #d8c6b5;

  border-radius: 10px;

  font-size: 16px;

  transition: .2s;

}


input:focus {

  outline: none;

  border-color: #556b2f;

  box-shadow: 0 0 0 4px rgba(85,107,47,.15);

}


button {

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


.back-button {

  background: transparent;

  color: #6b4226;

  border: 1px solid #d8c6b5;

}


.back-button:hover {

  background: #fff8ed;

}


@keyframes float {

  0% {

    transform: translate(0,0);

  }


  25% {

    transform: translate(25px,-20px);

  }


  50% {

    transform: translate(-20px,20px);

  }


  75% {

    transform: translate(20px,10px);

  }


  100% {

    transform: translate(0,0);

  }

}


</style>