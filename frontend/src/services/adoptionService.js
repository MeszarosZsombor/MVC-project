import api from './api'

export const getAdoptions = () => api.get('/adoptions')
export const getAdoption = (id) => api.get(`/adoptions/${id}`)
export const createAdoption = (adoption) => api.post('/adoptions', adoption)
export const updateAdoption = (id, adoption) => api.put(`/adoptions/${id}`, adoption)
export const updatePartiallyAdoption = (id, adoption) => api.patch(`/adoptions/${id}`, adoption)
export const deleteAdoption = (id) => api.delete(`/adoptions/${id}`)