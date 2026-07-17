<script setup lang="ts">

import Sidebar from "@/components/Sidebar.vue"

</script>


<template>

  <div class="layout">

    <Sidebar />


    <main class="content">

      <router-view />

    </main>


  </div>

</template>


<style scoped>

.layout {

  display: flex;

  min-height: 100vh;

}


.content {

  flex: 1;

  padding: 40px;

  background: #fff8ed;

}


</style>