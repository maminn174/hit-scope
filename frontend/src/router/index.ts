import { createRouter, createWebHistory } from 'vue-router'

import StartPage from "../views/StartPage.vue";
import MainPage from "../views/MainPage.vue";
import RegisterPage from "../views/RegisterPage.vue"

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      component: StartPage,
    },
    {
      path: '/register',
      component: RegisterPage,
    },
    {
      path: '/main',
      component: MainPage,
      meta: {
        requiresAuth: true
      }
    },
  ],
})

router.beforeEach(async (to) => {
  if (!to.meta.requiresAuth) {
    return true
  }

  try {
    const response = await fetch('http://localhost:8080/api/auth/me', {
      credentials: "include"
    })

    if (response.ok) {
      return true
    }

    return '/'
  } catch (error) {
    console.error(error)
    return '/'
  }
})

export default router
