<script setup>

import { ref } from "vue";
import Register from "../components/Register.vue";
import Login from "../components/Login.vue";

const activeTab = ref("login");
</script>

<template>
  <Login v-if="activeTab === 'login'" @switch="activeTab = 'register'" />
  <Register v-else @switch="activeTab = 'login'" />
</template>

<style scoped>

</style>