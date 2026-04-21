import axios from 'axios';
import { useAuthStore } from "../stores/auth.js";

const api = axios.create({
    baseURL: 'http://localhost:8080',
})

api.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    console.log("TOKEN:", token);
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

api.interceptors.response.use(
    response => response,
    error => {
        const authStore = useAuthStore();

        const isAuthRoute = error.config.url.includes("/auth");

        if (!isAuthRoute && (error.response?.status === 401)) {
            console.log("Unauthorized access... logging out user...");
            authStore.logout();
        } else if (!isAuthRoute && (error.response?.status === 403)){
            console.log("Something went wrong...");
            authStore.logout();
        }

        return Promise.reject(error);
    }
);

export default api
