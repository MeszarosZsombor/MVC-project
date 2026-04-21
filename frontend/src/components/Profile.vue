<script setup>
  import { ref } from 'vue';
  import { useAuthStore } from "../stores/auth.js";

  const auth = useAuthStore();
  const user = auth.user;
  const adoptions = ref([]);

</script>

<template>
  Welcome {{ user.name }}!

  Your adoptions:
  <div class="adoptions">
    <div v-for="adoption in adoptions" class="pet-card">
      <p>Pet name: </p>
      <p> Adoption date: </p>
    </div>
  </div>
</template>

<style scoped>

</style>