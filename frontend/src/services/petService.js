import api from './api'

export const getPets = () => api.get('/pets')
export const createPet = (pet) => api.post('/pets', pet)
export const updatePet = (id, pet) => api.put(`/pets/${id}`, pet)
export const deletePet = (id) => api.delete(`/pets/${id}`)