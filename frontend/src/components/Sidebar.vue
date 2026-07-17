<script setup lang="ts">

import { ref } from "vue"
import { useRouter } from "vue-router"

const router = useRouter()

const isOpen = ref(true)

const role = localStorage.getItem("role")


function toggleSidebar() {

  isOpen.value = !isOpen.value

}


function go(path: string) {

  router.push(path)

}


function logout() {

  localStorage.clear()

  router.push("/login")

}

</script>


<template>

  <aside
    class="sidebar"
    :class="{ collapsed: !isOpen }"
  >

    <button
      class="toggle"
      @click="toggleSidebar"
    >
      ☰
    </button>


    <h2 v-if="isOpen">
      CakeShop
    </h2>


    <button @click="go('/')">
    
      <span v-if="isOpen">
        Menu
      </span>

    </button>


    <button @click="go('/update-profile')">

      <span v-if="isOpen">
        Conta
      </span>

    </button>

    <button
      v-if="role === 'ADMIN'"
      @click="go('/admin')"
    >
    
      <span v-if="isOpen">
        Administração
      </span>

    </button>

    <button @click="logout">
      
      <span v-if="isOpen">
        Sair
      </span>

    </button>

  </aside>

</template>


<style scoped>

.sidebar {

  width: 240px;

  min-height: 100vh;

  background: #5c3b24;

  display: flex;

  flex-direction: column;

  gap: 15px;

  padding: 20px;

  transition: width .3s;

}

.sidebar.collapsed {

  width: 70px;

}

.toggle {

  align-self: flex-end;

  background: none;

  border: none;

  color: white;

  font-size: 24px;

  cursor: pointer;

}

h2 {

  color: white;

  text-align: center;

  margin-bottom: 30px;

}

button {

  display: flex;

  align-items: center;

  gap: 12px;

  padding: 14px;

  border: none;

  border-radius: 10px;

  background: transparent;

  color: white;

  cursor: pointer;

  font-size: 16px;

  transition: .2s;

}

button:hover {

  background: #6f4b31;

}

.sidebar.collapsed button {

  justify-content: center;

}

</style>