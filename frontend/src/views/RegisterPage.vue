<script setup lang="ts">
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'

const login = ref('')
const password = ref('')
const errorMessage = ref('')

const router = useRouter()

function goBack() {
  router.push('/')
}

async function submitRegister() {
  errorMessage.value = ''

  if (login.value === '' || password.value === '') {
    errorMessage.value = 'Заполните логин и пароль'
    return
  }

  try {
    await axios.post('http://localhost:8080/api/auth/register', {
      login: login.value,
      password: password.value,
    })

    router.push('/')
  } catch (error) {
    if (axios.isAxiosError(error) && error.response?.status === 409) {
      errorMessage.value = 'Пользователь с таким логином уже существует'
    }
  }
}
</script>

<template>
  <main class="register-page">
    <section class="register-page__content">
      <h1 class="register-page__title">
        Страница регистрации
      </h1>

      <p class="register-page__text">
        Создайте аккаунт, чтобы сохранять историю проверок
      </p>

      <form
          class="form"
          @submit.prevent="submitRegister"
      >
        <div class="form__inputs">
          <label>
            <input
                class="form__input"
                v-model="login"
                type="text"
                autocomplete="username"
                placeholder="Введите логин"
            />
          </label>

          <label>
            <input
                class="form__input"
                v-model="password"
                type="password"
                autocomplete="new-password"
                placeholder="Введите пароль"
            />
          </label>
        </div>

        <div class="form__actions">
          <p
              class="form__error"
              v-if="errorMessage"
          >
            {{ errorMessage }}
          </p>

          <button
              class="form__button form__button--primary"
              type="submit"
          >
            Зарегистрироваться
          </button>

          <button
              class="form__button form__button--secondary"
              type="button"
              @click="goBack"
          >
            Назад
          </button>
        </div>
      </form>
    </section>
  </main>
</template>

<style scoped lang="scss">
@use '../assets/styles/form.scss';

.register-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;

  &__content {
    width: 500px;
  }

  &__title {
    margin: 0 0 12px;
    color: #111827;
    font-size: 36px;
    text-align: center;
  }

  &__text {
    margin: 0 0 24px;
    color: #4b5563;
    text-align: center;
  }

  @media (max-width: 870px) {
    padding: 16px;

    &__content {
      width: 100%;
    }

    &__title {
      font-size: 28px;
    }
  }
}
</style>
