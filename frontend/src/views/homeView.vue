<script setup lang="ts">


import { createOrder } from "../service/orderService"
import { useRouter } from "vue-router"

const router = useRouter()


function go(path:string) {
  router.push(path)
}


async function handleCreateOrder() {

  try {

    const response = await createOrder()

    console.log(response)

    router.push(`/order/${response.id}`)

  } catch(error:any) {

    alert(error.response.data.message)

  }

}

const role = localStorage.getItem("role");

</script>


<template>

<div class="container">

  <h1>
    CakeShop
  </h1>

  <p class="subtitle">
    Painel de funcionalidades
  </p>


  <div class="grid">


    <div class="card">
      <h2>Conta</h2>

      <button @click="go('/create-account')">
        Criar conta
      </button>

      <button @click="go('/login')">
        Login
      </button>

      <button @click="go('/update-profile')">
        Meu perfil
      </button>

    </div>



    <div class="card">
      <h2>Produtos</h2>

      <button @click="go('/products')">
        Listar produtos
      </button>

      <button @click="go('/create-product')">
        Criar produto
      </button>

    </div>



    <div class="card">

      <h2>Pedidos</h2>

    <button @click="handleCreateOrder">
    Criar pedido
    </button>

    </div>

    
    <div 
  class="card"

  v-if="role === 'ADMIN'"
>

    <h2>Admin</h2>

    <button @click="go('/admin')">
      Administração
    </button>

  </div>


  </div>


</div>


</template>



<style scoped>

.container {

  min-height: 100vh;

  display: flex;

  flex-direction: column;

  align-items: center;

  justify-content: center;

  font-family: Arial, sans-serif;

}


h1 {

  font-size: 42px;

  margin-bottom: 10px;

}


.subtitle {

  margin-bottom: 40px;

  color: #666;

}


.grid {

  display: grid;

  grid-template-columns: repeat(3, 250px);

  gap: 25px;

}



.card {

  padding: 25px;

  border-radius: 12px;

  border: 1px solid #ddd;

  display: flex;

  flex-direction: column;

  gap: 12px;

}



.card h2 {

  margin-bottom: 10px;

}



button {

  padding: 12px;

  border: none;

  border-radius: 8px;

  cursor: pointer;

  background: #333;

  color: white;

}



button:hover {

  opacity: 0.8;

}


</style>