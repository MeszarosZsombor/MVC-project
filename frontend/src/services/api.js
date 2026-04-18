import axios from 'axios';
import { useAuthStore } from "../stores/auth.js";

const api = axios.create({
    baseURL: 'http://localhost:8080',
})

api.interceptors.request.use(config => {
    const authStore = useAuthStore()
    
    console.log("TOKEN:", localStorage.getItem("token"))
    console.log("INTERCEPTOR HIT");

    if (authStore.token) {
        config.headers.Authorization = `Bearer ${authStore.token}`
    }

    return config
})

export default api
