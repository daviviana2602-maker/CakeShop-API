<script setup lang="ts">

import { ref } from "vue";
import { showError, showSuccess, showWarning } from "@/service/notificationService";
import {
  searchUser,
  disableUser,
  reactivateUser,
  promoteUser,
  demoteUser
} from "../service/adminService";

import type { UserResponse } from "../service/adminService";


const identifier = ref("");

const user = ref<UserResponse | null>(null);

const loading = ref(false);



async function handleSearch() {


  if (!identifier.value.trim()) {

    showWarning("Identifier is required");

    return;

  }


  loading.value = true;

  user.value = null;


  try {


    user.value = await searchUser(identifier.value);


  } catch(error:any) {


    showError(error);


  } finally {


    loading.value = false;


  }


}



async function handleDisable() {


  if(!user.value) return;


  try {


    await disableUser(user.value.id);


    showSuccess("Usuário desativado");


    await handleSearch();


  } catch(error:any) {


    showError(error);


  }


}



async function handleReactivate() {


  if(!user.value) return;


  try {


    await reactivateUser(user.value.id);


    showSuccess("Usuário reativado");


    await handleSearch();


  } catch(error:any) {


    showError(error);


  }


}



async function handlePromote() {


  if(!user.value) return;


  try {


    await promoteUser(user.value.id);


    showSuccess("Usuário promovido");


    await handleSearch();


  } catch(error:any){

    showError(error);


  }


}



async function handleDemote() {


  if(!user.value) return;


  try {


    await demoteUser(user.value.id);


    showSuccess("Usuário rebaixado");


    await handleSearch();


  } catch(error:any) {


    showError(error);

  }


}


</script>



<template>

<div class="user-admin-page">


  <div class="search-card">

    <h1>
      Procurar Usuário
    </h1>


    <div class="search-area">

      <input
        v-model="identifier"
        placeholder="ID ou email do usuário"
      />


      <button @click="handleSearch">
        Buscar
      </button>

    </div>


  </div>



  <div 
    v-if="loading"
    class="loading"
  >
    Loading...
  </div>




  <div
    v-if="user"
    class="user-card"
  >


    <h2>
      Informações do Usuário
    </h2>


    <p>ID: {{ user.id }}</p>

    <p>Nome: {{ user.name }}</p>

    <p>Email: {{ user.email }}</p>

    <p>Role: {{ user.role }}</p>

    <p>Status: {{ user.status }}</p>

    <p>Criado em: {{ user.createdAt }}</p>



    <h3>
      Actions
    </h3>



    <div class="actions">


      <button @click="handleDisable">
        Desativar
      </button>


      <button @click="handleReactivate">
        Reativar
      </button>


      <button @click="handlePromote">
        Promover
      </button>


      <button @click="handleDemote">
        Rebaixar
      </button>


    </div>


  </div>


</div>

</template>



<style scoped>

.user-admin-page {

  width: 100%;

  display: flex;

  flex-direction: column;

  align-items: center;

  gap: 25px;

}


.search-card,
.user-card {

  width: 700px;

  max-width: 90%;

  background: white;

  padding: 30px;

  border-radius: 18px;

  border: 1px solid #eadbc8;

  box-shadow: 0 10px 25px rgba(107,66,38,.12);

}



h1 {

  color: #5c3b24;

  margin-bottom: 25px;

}



h2,
h3 {

  color: #556b2f;

  margin-bottom: 20px;

}



.search-area {

  display: flex;

  gap: 15px;

}



input {

  flex: 1;

  padding: 14px;

  border-radius: 10px;

  border: 1px solid #d8c6b5;

  font-size: 16px;

}



input:focus {

  outline: none;

  border-color: #556b2f;

  box-shadow: 0 0 0 4px rgba(85,107,47,.15);

}



button {

  padding: 12px 22px;

  border: none;

  border-radius: 12px;

  background: #556b2f;

  color: white;

  font-weight: 600;

  cursor: pointer;

  transition: .25s;

}



button:hover {

  background: #465a27;

  transform: translateY(-2px);

}



p {

  margin: 10px 0;

  color: #5c4a3d;

}



.loading {

  color: #556b2f;

  font-weight: bold;

}



.actions {

  display: flex;

  flex-wrap: wrap;

  gap: 12px;

  margin-top: 20px;

}


.actions button:first-child {

  background: #a0402d;

}


.actions button:first-child:hover {

  background: #803020;

}


</style>