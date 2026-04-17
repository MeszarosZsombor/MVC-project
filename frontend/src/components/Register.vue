<script setup>
  import { ref } from 'vue';
  import { createOwner } from "../services/ownerService.js";
  import Logo from "./Logo.vue";

  const email = ref("");
  const name = ref("");
  const password = ref("");
  const errorMessage = ref("");

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
      await createOwner({
        email: email.value.trim(),
        name: name.value.trim(),
        password: password.value.trim(),
      });
    }catch(err){
      errorMessage.value = "Registration failed";
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

    <p class="footer">Do you have an account already? Click <RouterLink to="/login">here</RouterLink> to continue</p>
  </div>
</template>


<style scoped>

  .card {
    font-family: 'DM Sans', sans-serif;
    background: white;
    border: 0.5px solid #e5e5e5;
    border-radius: 12px;
    padding: 2.5rem 2rem;
    width: 100%;
    max-width: 400px;
    margin: 0 auto;
  }

  .sub {
    font-size: 13px;
    color: #888;
    margin-bottom: 2rem;
    font-weight: 300;
  }

  .field {
    margin-bottom: 1.2rem;
  }

  label {
    display: block;
    font-size: 12px;
    font-weight: 500;
    letter-spacing: 0.06em;
    text-transform: uppercase;
    color: #888;
    margin-bottom: 6px;
  }

  input {
    width: 80%;
    padding: 10px 14px;
    font-size: 14px;
    background: #f7f7f5;
    border: 0.5px solid #e0e0e0;
    border-radius: 8px;
    color: black;
    outline: none;
    transition: border-color 0.15s, box-shadow 0.15s;
  }

  input:focus {
    border-color: #1D9E75;
    box-shadow: 0 0 0 3px rgba(29,158,117,0.12);
  }

  .btn {
    width: 100%;
    margin-top: 0.5rem;
    padding: 11px;
    font-size: 14px;
    font-weight: 500;
    background: #1D9E75;
    color: #fff;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    transition: background 0.15s, transform 0.1s;
  }

  .btn:hover {
    background: #0F6E56;
  }

  .btn:active {
    transform: scale(0.98);
  }

  .footer {
    text-align: center;
    margin-top: 1.5rem;
    font-size: 13px;
    color: #888;
  }

  .footer a {
    color: #1D9E75;
    text-decoration: none;
    font-weight: 500;
  }

  .error-msg {
    background: #fff0f0;
    color: #c0392b;
    border-radius: 8px;
    padding: 10px 14px;
    font-size: 13px;
    margin-bottom: 1rem;
  }
</style>