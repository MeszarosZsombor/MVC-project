<script setup>
import { onMounted, ref } from 'vue'
import {createPet, getPets} from '../services/petService'
import Logo from "../components/Logo.vue";
import {useAuthStore} from "../stores/auth.js";
import { getPetCategories } from "../services/petCategoryService.js";

const pets = ref([]);
const errorMessage = ref('');
const auth = useAuthStore();
const categories = ref([]);

const petName = ref('');
const weight = ref('');
const age = ref('');
const gender = ref('male');
const petCategoryId = ref('');
let isDisabled = false;

onMounted(async () => {
  const petsResponse = await getPets()
  pets.value = petsResponse.data.content || petsResponse.data;

  const categoriesResponse = await getPetCategories();
  categories.value = categoriesResponse.data.content || categoriesResponse.data;

  if (categories.value.length > 0) {
    petCategoryId.value = categories.value[0].id;
  } else {
    isDisabled = true;
    categories.value = ["Add a pet category first!"];
  }
})

function addPet() {
  errorMessage.value = "";

  try{
    createPet({
      petName: petName.value.trim(),
      weight: weight.value,
      age: age.value,
      gender: gender.value,
      petCategoryId: petCategoryId.value
    })

    petName.value = "";
    weight.value = "";
    age.value = "";
    gender.value = "male";
    petCategoryId.value = categories.value[0].id;
  }catch(e){
    errorMessage.value = e.message;
  }
}
</script>

<template>
  <h1>Pets</h1>

  <ul>
    <li v-for="pet in pets" :key="pet.petId">
      {{ pet.petName }} - {{ pet.age }} years
    </li>
  </ul>

  <div class="card">
    <Logo/>
    <p class="sub">Add a new pet</p>

    <div v-if="errorMessage" class="error-msg"> {{ errorMessage }} </div>

    <form @submit.prevent="addPet">
      <div class="field">
        <label for="petName">Name:</label>
        <input v-model="petName" type="text" id="petName" name="petName" autocomplete="off" placeholder="Name" />
      </div>
      <div class="field">
        <label for="weight">Weight: </label>
        <input v-model="weight" type="number"
               id="weight" name="weight" autocomplete="off" placeholder="Weight (in kg)" />
      </div>
      <div class="field">
        <label for="age">Age:</label>
        <input v-model="age" type="number" id="age" name="age" autocomplete="off" placeholder="Age" />
      </div>
      <div class="field">
        <label for="gender">Gender:</label>
        <select v-model="gender" name="gender" id="gender">
          <option value="male">Male</option>
          <option value="female">Female</option>
        </select>
      </div>
      <div class="field">
        <label for="petCategoryId">Pet Category: </label>
        <select v-model="petCategoryId" name="petCategoryId" id="petCategoryId">
          <option v-for="category in categories" :key="category.id" :value="category.id">{{ category.petType }}</option>
        </select>
      </div>
      <button v-if="isDisabled" class="btn" disabled> Add a pet category first! </button>
      <button type="submit" class="btn">Add new pet</button>
    </form>
  </div>
</template>