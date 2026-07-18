<script setup lang="ts">

import { onMounted, ref } from "vue"
import { listProducts, deleteProduct } from "../service/productService"
import { useRouter } from "vue-router"
import { showError, showSuccess } from "@/service/notificationService"
import { createOrder, addItem, listOrder, cancelOrder, concludeOrder } from "../service/orderService"


const products = ref<any[]>([])

const page = ref(0)
const totalPages = ref(0)

const role = localStorage.getItem("role")

const cart = ref<any[]>([])

const orderId = ref<number | null>(null)


onMounted(() => {
  loadProducts()
})

const router = useRouter()

function goToEdit(id: number) {
  router.push(`/edit-product/${id}`)
}

function nextPage() {
  page.value++
  loadProducts()
}

function previousPage() {
  page.value--
  loadProducts()
}


async function loadProducts() {

  try {

    const response = await listProducts(page.value)

    products.value = response.products
    totalPages.value = response.totalPages

  } catch (error: any) {

    showError(error)

  }

}



async function handleDelete(id: number) {

  try {

    await deleteProduct(id)

    showSuccess("Produto deletado!")

    loadProducts()

  } catch (error: any) {

    showError(error)

  }

}


async function handleCreateOrder() {

  try {

    const response = await createOrder()

    orderId.value = response.id

    await loadCart()

    showSuccess("Pedido iniciado!")

  } catch(error:any) {

    showError(error)

  }

}

async function loadCart() {

  if (orderId.value === null) {
    return
  }

  try {

    cart.value = await listOrder(orderId.value)

  } catch(error:any) {

    showError(error)

  }
  }


  function getQuantity(productId:number) {

  const item = cart.value.find(item => item.productId === productId)

  return item ? item.quantity : 0

}


async function updateCart(productId:number, quantity:number) {

  if(orderId.value === null){
    return
  }

  try {

    await addItem(orderId.value,{
      productId,
      quantity
    })

    await loadCart()

  } catch(error:any){

    showError(error)

  }

}


async function handleConcludeOrder() {

  if (orderId.value === null) {
    return
  }

  try {

    await concludeOrder(orderId.value)

    showSuccess("Pedido concluído!")

    orderId.value = null
    cart.value = []

  } catch(error:any) {

    showError(error)

  }

}


async function handleCancelOrder() {

  if (orderId.value === null) {
    return
  }

  try {

    await cancelOrder(orderId.value)

    showSuccess("Pedido cancelado!")

    orderId.value = null
    cart.value = []

  } catch(error:any) {

    showError(error)

  }

}


</script>


<template>

  <div class="layout">

    <Sidebar />

    <main class="content">

      <div class="header">

        <h1>Cardápio</h1>

        <button
          v-if="orderId === null"
          class="add-button"
          @click="handleCreateOrder"
        >
          Meu doce pedido aqui!
        </button>

      </div>

      <div class="products">

        <div
          class="product-card"
          v-for="product in products"
          :key="product.id"
        >

          <h3>{{ product.name }}</h3>

          <p class="price">
            R$ {{ product.price }}
          </p>

        <div v-if="orderId !== null"
        class="cart-controls"
        >

          <button
          class="remove-item"
          @click="updateCart(product.id, -1)"
        >
          -
        </button>

          <span>
            {{ getQuantity(product.id) }}
          </span>

          <button
          class="add-item"
          @click="updateCart(product.id, 1)"
        >
          +
        </button>

        </div>


          <button
            v-if="role === 'ADMIN'"
            @click="goToEdit(product.id)"
          >
            Editar
          </button>

          <button
            v-if="role === 'ADMIN'"
            @click="handleDelete(product.id)"
          >
            Excluir
          </button>

        </div>

      </div>


      <div
        v-if="orderId !== null"
        class="order-actions"
      >

        <button
          @click="handleConcludeOrder"
        >
          Concluir pedido
        </button>


        <button
          @click="handleCancelOrder"
        >
          Cancelar pedido
        </button>

      </div>


      <div class="pagination">

        <button
          @click="previousPage"
          :disabled="page === 0"
        >
          Anterior
        </button>

        <button
          @click="nextPage"
          :disabled="page + 1 >= totalPages"
        >
          Próxima
        </button>

      </div>

    </main>

  </div>

</template>



<style>

.layout {

  display: flex;

  min-height: 100vh;

}

.content {

  flex: 1;

  padding: 40px;

  background: #fff8ed;

}

.header {

  margin-bottom: 40px;

  display: flex;

  justify-content: space-between;

  align-items: center;

}

.header h1 {

  color: #6b4226;

  font-size: 40px;

}

.products {

  display: grid;

  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));

  gap: 25px;

}

.product-card {

  background: white;

  border-radius: 16px;

  padding: 20px;

  box-shadow: 0 8px 20px rgba(0, 0, 0, .08);

  display: flex;

  flex-direction: column;

  gap: 12px;

}

.product-card h3 {

  color: #5c3b24;

}

.price {

  font-size: 18px;

  font-weight: bold;

  color: #556b2f;

}

button {

  padding: 12px;

  border: none;

  border-radius: 10px;

  cursor: pointer;

}

.add-button {

  background: #556b2f;

  color: white;

}

.add-button:hover {

  background: #465a27;

}

.pagination {

  margin-top: 40px;

  display: flex;

  justify-content: center;

  gap: 20px;

}

.order-actions {

  margin-top: 40px;

  display: flex;

  justify-content: center;

  gap: 20px;

}

.remove-item {

  background: #f8d7da;

  color: #b02a37;

}

.remove-item:hover {

  background: #dc3545;

  color: white;

}


.add-item {

  background: #d1e7dd;

  color: #146c43;

}

.add-item:hover {

  background: #198754;

  color: white;

}


.cart-controls {

  display: flex;

  align-items: center;

  gap: 15px;

}


</style>