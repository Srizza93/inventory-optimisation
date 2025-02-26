import { createRouter, createWebHistory } from 'vue-router'
import OptimizationView from '../views/OptimizationView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'optimization',
      component: OptimizationView,
    },
  ],
})

export default router
