<script setup lang="ts">

import { ref } from "vue"
import { updateProfile, updatePassword, deleteProfile } from "../service/profileService"
import { showSuccess, showError } from "@/service/notificationService"
import { useRouter } from "vue-router"


const name = ref("")
const newEmail = ref("")


const currentPassword = ref("")
const newPassword = ref("")

const router = useRouter()

function go(path: string) {

  router.push(path)


};


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

    go("/login")

  } catch(error:any) {

    showError(error);

  }

}

</script>


<template>

<div class="profile-page">

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

</div>

</template>



<style scoped>

h1 {

  color: #5c3b24;

  font-size: 38px;

  margin-bottom: 35px;

}



section {

  background: white;

  padding: 30px;

  border-radius: 18px;

  margin-bottom: 25px;

  max-width: 700px;

  box-shadow: 0 10px 25px rgba(107, 66, 38, 0.12);

  border: 1px solid #eadbc8;

}



h2 {

  color: #556b2f;

  font-size: 24px;

  margin-bottom: 20px;

}



input {

  width: 100%;

  padding: 14px;

  margin-bottom: 15px;

  border-radius: 10px;

  border: 1px solid #d8c6b5;

  font-size: 16px;

  background: #fffdf9;

  transition: .2s;

}



input:focus {

  outline: none;

  border-color: #556b2f;

  box-shadow: 0 0 0 4px rgba(85,107,47,.15);

}



button {

  padding: 13px 22px;

  border: none;

  border-radius: 12px;

  background: #556b2f;

  color: white;

  font-size: 15px;

  font-weight: 600;

  cursor: pointer;

  transition: .25s;

}



button:hover {

  background: #465a27;

  transform: translateY(-2px);

}



section:last-child {

  border: 1px solid #e6b8a8;

}



section:last-child h2 {

  color: #a0402d;

}



section:last-child button {

  background: #a0402d;

}



section:last-child button:hover {

  background: #803020;

}


.profile-page {

  width: 100%;

  display: flex;

  flex-direction: column;

  align-items: center;

}


</style>