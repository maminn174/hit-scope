<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const login = ref('')
const password = ref('')
const error = ref('')
const isLoading = ref(false)

async function submitLogin() {
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

function goToRegister() {
  router.push('/register')
}

</script>

<template>
  <main class="start-page">
    <section class="start-page__content">
      <h1 class="start-page__title">
        HitScope
      </h1>

      <p class="start-page__text">
        Интерактивная проверка точек на координатной плоскости
      </p>

      <form
          class="form"
          @submit.prevent="submitLogin"
      >
        <h2>Войти в проект</h2>
        <div class="form__inputs">
          <label>
            <input
              class="form__input"
              v-model="login"
              type="text"
              autocomplete="username"
              placeholder="Введите логин"
            >
          </label>
          <label>
            <input
                class="form__input"
                v-model="password"
                type="password"
                autocomplete="current-password"
                placeholder="Введите пароль"
            >
          </label>
        </div>



        <div class="form__actions">
          <p
              class="form__error"
              v-if="error"
          >
            {{ error }}
          </p>
          <button
              class="form__button form__button--primary"
              type="submit"
              :disabled="isLoading"
          >
            {{ isLoading ? 'Вход...' : 'Войти' }}
          </button>
          <button
              class="form__button form__button--secondary"
              type="button"
              @click="goToRegister"
          >
            Зарегистрироваться
          </button>
        </div>
      </form>


    </section>
  </main>
</template>

<style scoped lang="scss">
@use '../assets/styles/form.scss';

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
    color: #4b5563;
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
