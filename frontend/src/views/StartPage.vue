<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const login = ref('')
const password = ref('')
const error = ref('')
const isLoading = ref(false)

const submitLogin = async () => {
  isLoading.value = true
  error.value = ''

  try {
    const response = await fetch('http://localhost:8080/api/auth/login', {
      method: 'POST',
      credentials: 'include',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        login: login.value,
        password: password.value,
      })
    })

    if (!response.ok) {
      if (response.status === 401) {
        error.value = 'Неверный логин или пароль'
        return
      }

      throw new Error(`Ошибка запроса: ${response.status}`)
    }

    await router.push('/main')
  } catch (err) {
    console.error(err)
    error.value = 'Не удалось выполнить вход'
  } finally {
    isLoading.value = false
  }
}
</script>

<template>
  <main class="start-page">
    <section class="start-page__content">
      <h1 class="start-page__title">
        Лабораторная работа
      </h1>

      <p class="start-page__text">
        Vue 3 + Spring Boot
      </p>

      <form @submit.prevent="submitLogin">
        <div>
          <label>
            Логин
            <input
              v-model="login"
              type="text"
              autocomplete="username"
            >
          </label>
        </div>
        <div>
          <label>
            Пароль
            <input
                v-model="password"
                type="password"
                autocomplete="current-password"
            >
          </label>
        </div>

        <p v-if="error">
          {{ error }}
        </p>

        <button
          type="submit"
          :disabled="isLoading"
        >
          {{ isLoading ? 'Вход...' : 'Войти' }}
        </button>
      </form>
    </section>
  </main>
</template>

<style scoped lang="scss">
.start-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;

  &__content {
    width: 600px;
  }

  &__title {
    margin: 0 0 16px;
    font-size: 40px;
  }

  &__text {
    margin-bottom: 24px;
  }

  &__button {
    padding: 12px 20px;
    cursor: pointer;
  }

  @media (max-width: 1209px) {
    padding: 24px;

    &__content {
      width: 500px;
    }

    &__title {
      font-size: 32px;
    }
  }

  @media (max-width: 870px) {
    padding: 16px;

    &__content {
      width: 100%;
    }

    &__title {
      font-size: 26px;
    }

    &__button {
      width: 100%;
    }
  }
}
</style>