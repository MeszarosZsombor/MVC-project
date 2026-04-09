import { createRouter, createWebHistory } from 'vue-router'

import Users from '../views/Users.vue'
import PetCategories from '../views/PetCategories.vue'
import Pets from '../views/Pets.vue'
import Adoptions from '../views/Adoptions.vue'

const routes = [
    {path: '/', redirect: '/pets' },
    { path: '/users', component: Users },
    { path: '/pet-categories', component: PetCategories },
    { path: '/pets', component: Pets },
    { path: '/adoptions', component: Adoptions }
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router