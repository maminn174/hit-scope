<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import CoordinatePlane from '../components/CoordinatePlane.vue'
import axios from 'axios'

type PointResult = {
  id: number
  x: number
  y: number
  r: number
  hit: boolean
}
type Point = {
  id: number
  x: number
  y: number
  r: number
  hit: boolean
}

const router = useRouter()

const points = ref<Point[]>([])
const isLoading = ref(false)
const error = ref('')
const results = ref<PointResult[]>([])

const filteredResults = computed(() => {
  return results.value.filter((result) => result.r === r.value)
})

const x = ref(0)
const y = ref('')
const r = ref(1)

const errorMessage = ref('')

const selectValues = [-4, -3, -2, -1, 0, 1, 2, 3, 4]

function formatCoordinate(value: number) {
  return value.toFixed(2)
}

function handleReset() {
  x.value = 0
  y.value = ''
  r.value = 1
  errorMessage.value = ''
}

async function logout() {
  try {
    const response = await fetch('http://localhost:8080/api/auth/logout', {
      method: 'POST',
      credentials: 'include',
    })

    if (!response.ok) {
      throw new Error(`Ошибка выхода: ${response.status}`)
    }

    await router.push('/')
  } catch (error) {
    console.error(error)
  }
}

async function loadPoints() {
  isLoading.value = true
  error.value = ''

  try {
    const response = await fetch('http://localhost:8080/api/points')

    if (!response.ok) {
      throw new Error(`Ошибка запроса: ${response.status}`)
    }

    points.value = await response.json()
  } catch (err) {
    error.value = 'Не удалось загрузить точки'
    console.error(err)
  } finally {
    isLoading.value = false
  }
}

async function addPoint() {
  const response = await fetch("http://localhost:8080/api/points", {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      x: x.value,
      y: y.value,
      r: r.value,
    })
  })

  if (!response.ok) {
    throw new Error(`Ошибка запроса: ${response.status}`)
  }

  await loadPoints()
}

async function handlePointSelect(x: number, y:number) {
  await submitPoint(x, y)
}

async function loadResults() {
  try {
    const response = await axios.get<PointResult[]>('http://localhost:8080/api/points')
    
    results.value = response.data
  } catch (error) {
    console.error('Ошибка при загрузке результатов:', error)
  }
}

async function submitPoint(xValue: number, yValue: number) {
  try {
    const response = await axios.post<PointResult>('http://localhost:8080/api/points', {
      x: xValue,
      y: yValue,
      r: r.value,
    })

    results.value.push(response.data)
  } catch (error) {
    console.error('Ошибка при отправке точки:', error)
  }
}

async function handleSubmit() {
  errorMessage.value = ''

  const yValue = Number(y.value)

  if (y.value.trim() === '') {
    errorMessage.value = 'Введите координату Y'
    return
  }

  if (Number.isNaN(yValue)) {
    errorMessage.value = 'Y должен быть числом'
    return
  }

  if (yValue < -3 || yValue > 3) {
    errorMessage.value = 'Y должен находиться в диапазоне от -3 до 3'
    return
  }

  if (r.value <= 0) {
    errorMessage.value = 'Радиус должен быть больше 0'
    return
  }

  await submitPoint(x.value, yValue)
}

async function clearResults() {
  try {
    await axios.delete('http://localhost:8080/api/points')

    results.value = []
  } catch (error) {
    console.error('Ошибка при очистке результатов:', error)
  }
}

onMounted(() => {
  loadPoints()
})

onMounted(() => {
  loadResults()
})

</script>

<template>
  <main>
    <div class="header">
      <h1>Основная страница</h1>
      <button
          type="button"
          @click="logout"
      >
        Выйти
      </button>
    </div>

    <div class="main-content">
      <CoordinatePlane
          class="svg"
          :r="r"
          :points="filteredResults"
          @select-point="handlePointSelect"
      />

      <div class="point-form">
        <label>
          X
          <select v-model.number="x" class="input-height">
            <option
                v-for="value in selectValues"
                :key="value"
                :value="value"
            >
              {{ value }}
            </option>
          </select>
        </label>

        <label>
          Y
          <input
              v-model="y"
              type="text"
              placeholder="-3 ... 3"
              class="input-height"
          />
        </label>

        <label>
          R
          <select v-model.number="r" class="input-height">
            <option
                v-for="value in selectValues"
                :key="value"
                :value="value"
            >
              {{ value }}
            </option>
          </select>
        </label>

        <p
            v-if="errorMessage"
            class="error-message"
        >
          {{ errorMessage }}
        </p>

        <div class="form-actions">
          <button @click="handleSubmit">
            Проверить точку
          </button>

          <button @click="handleReset">
            Сбросить
          </button>
        </div>

        <button @click="clearResults">
          Очистить все проверки
        </button>
      </div>
    </div>

    <table>
      <thead>
        <tr>
          <th>X</th>
          <th>Y</th>
          <th>R</th>
          <th>Результат</th>
        </tr>
      </thead>

      <tbody>
        <tr
            v-for="result in results"
            :key="result.id"
        >
          <td>{{ formatCoordinate(result.x) }}</td>
          <td>{{ formatCoordinate(result.y) }}</td>
          <td>{{ result.r }}</td>
          <td>
            {{ result.hit ? 'Попадание' : 'Промах' }}
          </td>
        </tr>
      </tbody>
    </table>

    <p v-if="isLoading">Загрузка...</p>

    <p v-else-if="error">{{ error }}</p>

    <RouterLink to="/">
      <button
          type="button"
      >
        Назад
      </button>

    </RouterLink>


  </main>
</template>

<style scoped lang="scss">
@use '../assets/styles/mixins' as *;

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th,
td {
  padding: 8px 12px;
  border: 1px solid #ccc;
  text-align: center;
}

.header {
  display: flex;
  justify-content: space-between;
}

.svg {
  @include tablet {
    justify-self: center;
  }
}

.main-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 40px;

  @include tablet {
    grid-template-columns: 1fr;
  }
}

.point-form {
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-width: 220px;
}

.point-form label {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.form-actions {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 10px;
}

.error-message {
  margin: 0;
  color: red;
  font-size: 14px;
}

.input-height {
  height: 50px;
  font-size: 25px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
