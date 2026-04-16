<script setup>
import { onMounted, ref } from 'vue'
import { getPets } from '../services/petService'

const pets = ref([])

onMounted(async () => {
  const response = await getPets()
  pets.value = response.data.content || response.data
})
</script>

<template>
  <h1>Pets</h1>

  <ul>
    <li v-for="pet in pets" :key="pet.petId">
      {{ pet.petName }} - {{ pet.age }} years
    </li>
  </ul>

  <h2>Add Pet</h2>
</template>