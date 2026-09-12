<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const login = ref('')
const password = ref('')
const errorMessage = ref('')

const router = useRouter()


async function submitRegister() {
  errorMessage.value = ''

  if (login.value === '' || password.value === '') {
    errorMessage.value = 'Заполните логин и пароль'
    return
  }

  try {
    await axios.post("http://localhost:8080/api/auth/register", {
      login: login.value,
      password: password.value,
    })

    router.push("/")
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 409) {
      errorMessage.value = 'Пользователь с таким логином уже существует'
    }
  }
}

</script>

<template>
  <h1>Страница регистрации</h1>

  <form @submit.prevent="submitRegister">
    <label>
      Введите логин
      <input
          v-model="login"
          type="text"
      />
    </label>
    <label>
      Введите пароль
      <input
          v-model="password"
          type="password"
      />
    </label>
    <button
      type="submit"
    >
      Зарегистрироваться
    </button>
  </form>
  <p v-if="errorMessage">
    {{ errorMessage }}
  </p>
</template>