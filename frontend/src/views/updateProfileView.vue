<script setup lang="ts">

import { ref } from "vue"
import { updateProfile, updatePassword, deleteProfile } from "../service/profileService"
import { showSuccess, showError } from "@/service/notificationService"
import { handleApiError } from "@/errorControl/handleApiError"


const name = ref("")
const newEmail = ref("")


const currentPassword = ref("")
const newPassword = ref("")


async function handleUpdateProfile() {

  const data = {
  name: name.value,
  newEmail: newEmail.value
  }


  try {

    await updateProfile(data)

    showSuccess("Perfil atualizado!")

    name.value = ""
    newEmail.value = ""


  } catch(error:any) {

    showError(error);

  }

}


async function handleUpdatePassword() {

  const data = {
    currentPassword: currentPassword.value,
    newPassword: newPassword.value
  }


  try {

    await updatePassword(data)

    showSuccess("Senha alterada!")

    currentPassword.value = ""
    newPassword.value = ""

  } catch(error:any) {

    showError(error);

  }

}


async function handleDeleteProfile() {

  const confirmDelete = confirm(
    "Deseja realmente excluir sua conta?"
  )

  if (!confirmDelete) {
    return
  }


  try {

    await deleteProfile()

    localStorage.removeItem("accessToken")
    localStorage.removeItem("refreshToken")

    showSuccess("Conta deletada!")

  } catch(error:any) {

    showError(error);

  }

}

</script>


<template>

<h1>Meu perfil</h1>


<section>

<h2>Editar informações</h2>

<input
  v-model="name"
  placeholder="Nome"
/>

<input
  v-model="newEmail"
  placeholder="Novo email"
/>

<button @click="handleUpdateProfile">
  Salvar mudanças
</button>

</section>



<section>

<h2>Alterar senha</h2>

<input
  v-model="currentPassword"
  type="password"
  placeholder="Senha atual"
/>

<input
  v-model="newPassword"
  type="password"
  placeholder="Nova senha"
/>

<button @click="handleUpdatePassword">
  Alterar senha
</button>

</section>



<section>

<h2>Excluir conta</h2>

<button @click="handleDeleteProfile">
  Deletar conta
</button>

</section>


</template>