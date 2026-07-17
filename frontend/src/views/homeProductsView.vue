<script setup lang="ts">

import { onMounted, ref } from "vue"
import { listProducts } from "../service/productService"
import { useRouter } from "vue-router"
import { deleteProduct } from "../service/productService"
import { showError, showSuccess } from "@/service/notificationService"


const products = ref<any[]>([])

const page = ref(0);
const totalPages = ref(0);

const role = localStorage.getItem("role");


onMounted(() => {loadProducts()})


const router = useRouter()

function goToEdit(id: number) {
  router.push(`/edit-product/${id}`)
}


function nextPage() {

  page.value++;
  loadProducts();

}


function previousPage() {

  page.value--;
  loadProducts();

}


async function loadProducts() {

  try {

    const response = await listProducts(page.value)

    products.value = response.products;
    totalPages.value = response.totalPages;

  } catch(error:any) {

    showError(error);

  }

}



async function handleDelete(id: number) {

  try {

    await deleteProduct(id)

    showSuccess("Produto deletado!")

    loadProducts()

  } catch(error:any) {

    showError(error);

  }

}


</script>



<template>

  <div class="layout">

    <Sidebar />

    <main class="content">

      <div class="header">

        <h1>Cardápio</h1>

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

          <button class="add-button">
            Adicionar ao carrinho
          </button>

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

</style>