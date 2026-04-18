<script setup>

import Logo from "./Logo.vue";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "../stores/auth.js";
import {loginOwner} from "../services/ownerService.js";

const auth = useAuthStore();
const router = useRouter();
const email = ref("")
const password = ref("")
const errorMessage = ref("")

const login = async () => {
  try {
    const response = await loginOwner({
      email: email.value.trim(),
      password: password.value.trim()
    });

    auth.login(response.data.user, response.data.token);
    router.push("/pet-categories");
  }catch(err) {
    errorMessage.value = err.message;
  }
}

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