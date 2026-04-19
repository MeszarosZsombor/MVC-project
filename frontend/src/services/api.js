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

api.interceptors.response.use(
    response => response,
    error => {
        const authStore = useAuthStore();

        const isAuthRoute = error.config.url.includes("/auth");

        //could be 403 as well?
        if (!isAuthRoute && error.response?.status === 401) {
            console.log("Unauthorized access... logging out user...");
            authStore.logout();
        }

        return Promise.reject(error);
    }
);

export default api
