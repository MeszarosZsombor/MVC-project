<script setup>
import Logo from "./Logo.vue";
</script>

<template>
  <header>
    <Logo class="logo"></Logo>
    <router-link to="/users" class="site-button">Users</router-link>
    <router-link to="/pet-categories" class="site-button">Pet Categories</router-link>
    <router-link to="/pets" class="site-button">Pets</router-link>
    <router-link to="/adoptions" class="site-button">Adoptions</router-link>
  </header>
</template>

<style scoped>
  header {
    font-family: 'DM Sans', sans-serif;
    margin-top: 20px;
    margin-left: 20px;
    display: flex;
    margin-bottom: 20px;
  }

  .logo {
    align-self: center;
    margin-right: 20px;
  }

  .site-button {
    margin-right: 10px;
    cursor: pointer;
    text-decoration: none;
    color: black;
    background-color: white;
    padding: 6px;
    border-radius: 7px;
  }
</style>