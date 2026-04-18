import api from './api'

export const getOwners = () => api.get('/owners')
export const getOwner = (id) => api.get(`/owners/${id}`)
export const createOwner = (owner) => api.post('/owners', owner)
export const updateOwner = (id, owner) => api.put(`/owners/${id}`, owner)
export const updatePartiallyOwner = (id, owner) => api.patch(`/owners/${id}`, owner)
export const deleteOwner = (id) => api.delete(`/owners/${id}`)
