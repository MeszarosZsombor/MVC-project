import api from './api'

export const getPetCategories = () => api.get('/petCategories')
export const getPetCategory = (id) => api.get(`/petCategories/${id}`)
export const createPetCategory = (petCategory) => api.post('/petCategories', petCategory)
export const updatePetCategory = (id, petCategory) => api.put(`/petCategories/${id}`, petCategory)
export const updatePartiallyPetCategory = (id, petCategory) => api.patch(`/petCategories/${id}`, petCategory)
export const deletePetCategory = (id) => api.delete(`/petCategories/${id}`)