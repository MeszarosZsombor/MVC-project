import api from './api'

export const getPetCategories = () => api.get('/pet_categories')
export const getPetCategory = (id) => api.get(`/pet_categories/${id}`)
export const createPetCategory = (petCategory) => api.post('/pet_categories', petCategory)
export const updatePetCategory = (id, petCategory) => api.put(`/pet_categories/${id}`, petCategory)
export const updatePartiallyPetCategory = (id, petCategory) => api.patch(`/pet_categories/${id}`, petCategory)
export const deletePetCategory = (id) => api.delete(`/pet_categories/${id}`)