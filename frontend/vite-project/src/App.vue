<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const adoptions = ref([])

onMounted(async () => {
  const response = await axios.get('http://localhost:8080/adoptions')
  adoptions.value = response.data.content
})
</script>

<template>
  <h1>Adoptions</h1>

  <ul>
    <li v-for="a in adoptions" :key="a.adoptionId">
      Adoption ID: {{ a.adoptionId }} | Pet: {{ a.petId }}
    </li>
  </ul>
</template>