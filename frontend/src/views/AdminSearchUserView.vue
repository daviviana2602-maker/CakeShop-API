<script setup lang="ts">

import { ref } from "vue";
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

    alert("Identifier is required");

    return;

  }


  loading.value = true;

  user.value = null;


  try {


    user.value = await searchUser(
      identifier.value
    );


  } catch(error:any) {


    alert(error.response.data.message)


  } finally {


    loading.value = false;


  }


}



async function handleDisable() {


  if(!user.value) return;



  try {


    await disableUser(
      user.value.id
    );


    alert("User disabled");


    await handleSearch();


  } catch(error:any) {


    alert(error.response.data.message)


  }


}



async function handleReactivate() {


  if(!user.value) return;


  try {


    await reactivateUser(
      user.value.id
    );


    alert("User reactivated");


    await handleSearch();


  } catch(error:any) {


    alert(error.response.data.message)


  }


}



async function handlePromote() {


  if(!user.value) return;


  try {


    await promoteUser(
      user.value.id
    );


    alert("User promoted");


    await handleSearch();


  } catch(error:any){

    alert(error.response.data.message)


  }


}



async function handleDemote() {


  if(!user.value) return;


  try {


    await demoteUser(
      user.value.id
    );


    alert("User demoted");


    await handleSearch();


  } catch(error:any) {


    alert(error.response.data.message)

  }


}


</script>



<template>

<div>


<h1>
  Admin User Search
</h1>



<input
  v-model="identifier"
  placeholder="User ID or email"
/>


<button @click="handleSearch">
  Search
</button>




<div v-if="loading">

Loading...

</div>





<div v-if="user">


<h2>
User Information
</h2>



<p>
ID: {{ user.id }}
</p>


<p>
Name: {{ user.name }}
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
Created at: {{ user.createdAt }}
</p>




<h3>
Actions
</h3>



<button
@click="handleDisable"
>
Disable
</button>



<button
@click="handleReactivate"
>
Reactivate
</button>



<button
@click="handlePromote"
>
Promote
</button>



<button
@click="handleDemote"
>
Demote
</button>



</div>


</div>


</template>