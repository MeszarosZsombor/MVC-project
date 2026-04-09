<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import Header from "./components/Header.vue";

</script>

<template>
  <Header/>
  <router-view/>
</template>

<style scoped>
</style>