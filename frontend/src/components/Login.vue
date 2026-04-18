<script setup>

import Logo from "./Logo.vue";

const emit = defineEmits(["switch"]);
</script>

<template>
  <div class="card">
    <Logo></Logo>
    <p class="sub">Login</p>

    <div v-if="errorMessage" class="error-msg"> {{ errorMessage }} </div>

    <form @submit.prevent="login">
      <div class="field">
        <label for="email">Email</label>
        <input v-model="email" type="email" name="email" id="email" placeholder="Email" required/>
      </div>
      <div class="field">
        <label for="password">Password</label>
        <input v-model="password" type="password" name="password" id="password" placeholder="Password" required/>
      </div>
      <button type="submit" class="btn">Login</button>
    </form>

    <p class="footer">Don't have an account? Click <a href="#" @click.prevent="emit('switch')" >here</a> to register</p>
  </div>
</template>

<style scoped>

</style>