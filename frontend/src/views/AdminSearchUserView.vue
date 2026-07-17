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

<div>


<h1>
  Procurar Usuário
</h1>



<input
  v-model="identifier"
  placeholder="ID ou email do usuário "
/>


<button @click="handleSearch">
  Buscar
</button>




<div v-if="loading">

Loading...

</div>





<div v-if="user">


<h2>
Informações do Usuário
</h2>



<p>
ID: {{ user.id }}
</p>


<p>
Nome: {{ user.name }}
</p>


<p>
Email: {{ user.email }}
</p>


<p>
Role: {{ user.role }}
</p>


<p>
Status: {{ user.status }}
</p>


<p>
Criado em: {{ user.createdAt }}
</p>




<h3>
Actions
</h3>



<button
@click="handleDisable"
>
Desativar
</button>



<button
@click="handleReactivate"
>
Reativar
</button>



<button
@click="handlePromote"
>
Promover
</button>



<button
@click="handleDemote"
>
Rebaixar
</button>



</div>


</div>


</template>