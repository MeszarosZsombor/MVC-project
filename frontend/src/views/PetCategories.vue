<script setup>
import { ref, onMounted } from "vue";
import { createPetCategory, getPetCategories } from "../services/petCategoryService";

const categories = ref([]);
const petType = ref("");
const errorMessage = ref("");

const loadCategories = async () => {
  try {
    const response = await getPetCategories();
    categories.value = response.data.content;
    console.log(categories.value)
  }catch(error) {
    console.error(error);
  }
}

const addCategory = async () => {
  errorMessage.value = "";

  if (!petType.value.trim()) return;

  try{
    const newCategory = await createPetCategory({
      petType: petType.value,
    })

    categories.value.push(newCategory.data)

    petType.value = "";
  }catch(err){
    if (err.response && err.response.status === 409) {
      errorMessage.value = "Category already exists"
    } else {
      errorMessage.value = "Something went wrong"
    }
  }
}

onMounted(loadCategories);
</script>

<template>
  <h1>Pet Categories</h1>

  <ul>
    <li v-for="category in categories"> {{category.petType}} </li>
  </ul>

  <h2>Add Category</h2>
  <p v-if="errorMessage" style="color: red;"> {{ errorMessage }} </p>
  <form @submit.prevent="addCategory">
    <input v-model="petType" type="text" placeholder="Category Name" required />
    <button type="submit" :disabled="!petType.trim()">Add Category</button>
  </form>
</template>

<style scoped>

</style>