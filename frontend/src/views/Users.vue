<script setup>

import { ref } from "vue";
import Register from "../components/Register.vue";
import Login from "../components/Login.vue";
import { useAuthStore } from "../stores/auth.js";
import Profile from "../components/Profile.vue";

const activeTab = ref("login");
const auth = useAuthStore();
</script>

<template>
  <Profile v-if="auth.isLoggedIn"/>

  <div v-else>
    <Login v-if="activeTab === 'login'" @switch="activeTab = 'register'" />
    <Register v-else @switch="activeTab = 'login'" />
  </div>
</template>

<style scoped>

</style>