import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const useAuthStore = defineStore('auth', () => {
    const token = ref(localStorage.getItem('token') || null);
    const user = ref(JSON.parse(localStorage.getItem('user') || 'null'));

    const isLoggedIn = computed(() => !!token.value);

    function login(userData, authToken) {
        token.value = authToken;
        user.value = userData;
        localStorage.setItem('token', authToken);
        localStorage.setItem('user', JSON.stringify(userData));
    }

    function logout() {
        token.value = null;
        user.value = null;
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    }

    return { token, user, isLoggedIn, login, logout };
});