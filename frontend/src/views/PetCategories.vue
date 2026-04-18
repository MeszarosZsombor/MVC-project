<script setup>
import {ref, onMounted, computed} from "vue";
import { createPetCategory, getPetCategories } from "../services/petCategoryService";
import Logo from "../components/Logo.vue";

const categories = ref([]);
const petType = ref("");
const errorMessage = ref("");

const isDisabled = computed(() => !petType.value.trim());

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

  if (!petType.value.trim()) {
    errorMessage.value = "Category cannot be empty";
    return;
  }

  try{
    const newCategory = await createPetCategory({
      petType: petType.value.trim(),
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

  <div class="card">
    <Logo></Logo>
    <p class="sub"> Add Category </p>
    <p v-if="errorMessage" class="error-msg"> {{ errorMessage }} </p>
    <form @submit.prevent="addCategory">
      <input v-model="petType" type="text" placeholder="Category Name" required />
      <button type="submit" class="btn" :disabled="isDisabled">Add Category</button>
    </form>
  </div>

  <ul>
    <li v-for="category in categories"> {{category.petType}} </li>
  </ul>
</template>

<style scoped>

</style>