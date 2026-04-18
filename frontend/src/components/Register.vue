<script setup>
  import { ref } from 'vue';
  import { useRouter } from "vue-router";
  import { useAuthStore } from "../stores/auth.js";
  import { registerOwner } from "../services/ownerService.js";
  import Logo from "./Logo.vue";

  const auth = useAuthStore();
  const router = useRouter();
  const email = ref("");
  const name = ref("");
  const password = ref("");
  const errorMessage = ref("");

  const emit = defineEmits(["switch"]);

  const register = async () => {
    errorMessage.value = "";

    if(email.value.trim() === ""){
      errorMessage.value = "Email cannot be empty";
    }

    if(name.value.trim() === ""){
      errorMessage.value = "Name cannot be empty";
    }

    if(password.value.trim() === ""){
      errorMessage.value = "Password cannot be empty";
    }

    try{
      const response = await registerOwner({
        email: email.value.trim(),
        name: name.value.trim(),
        password: password.value.trim(),
      });
      
      auth.login(response.data, response.data.token);
      router.push('/pet-categories');
    }catch(err){
      errorMessage.value = "Registration failed";
      console.error(err);
    }
  }
  
</script>

<template>
  <div class="card">
    <Logo></Logo>
    <p class="sub">Create an Owner account</p>

    <div v-if="errorMessage" class="error-msg"> {{ errorMessage }} </div>

    <form @submit.prevent="register">
      <div class="field">
        <label for="email">Email</label>
        <input v-model="email" type="email" name="email" id="email" placeholder="Email" required/>
      </div>
      <div class="field">
        <label for="name">Name</label>
        <input v-model="name" type="text" name="name" id="name" placeholder="Name" required/>
      </div>
      <div class="field">
        <label for="password">Password</label>
        <input v-model="password" type="password" name="password" id="password" placeholder="Password" required/>
      </div>
      <button type="submit" class="btn">Register</button>
    </form>

    <p class="footer">Do you have an account already? Click <a href="#" @click.prevent="emit('switch')">here</a> to continue</p>
  </div>
</template>


<style scoped>
</style>
