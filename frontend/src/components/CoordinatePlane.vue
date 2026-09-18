<script
    setup
    lang="ts"
>

type PointResult = {
  id: number
  x: number
  y: number
  r: number
  hit: boolean
}

const size = 400
const center = size / 2
const step = 50

const ticks = [-3, -2, -1, 1, 2, 3]

const props = defineProps<{
  r: number
  points: PointResult[]
}>()


const emit = defineEmits<{
  selectPoint: [x: number, y: number]
}>()

function toSvgX(x: number) {
  return center + x * step
}

function toSvgY(y: number) {
  return center - y * step
}

function toMathX(svgX: number) {
  return (svgX - center) / step
}

function toMathY(svgY: number) {
  return (center - svgY) / step
}

function handleClick(event: MouseEvent) {
  const svg = event.currentTarget as SVGSVGElement
  const rect = svg.getBoundingClientRect()

  const svgX = event.clientX - rect.left
  const svgY = event.clientY - rect.top

  const x = toMathX(svgX)
  const y = toMathY(svgY)

  emit('selectPoint', x, y)
}

</script>

<template>

  <svg
      :width="size"
      :height="size - 20"
      :viewBox="`0 0 ${size} ${size}`"
      class="coordinate-plane"
      @click="handleClick"
  >

    <!-- Ось X -->
    <line
        x1="20"
        :y1="center"
        :x2="size - 20"
        :y2="center"
        stroke="black"
        stroke-width="2"
    />

    <!-- Ось Y -->
    <line
        :x1="center"
        y1="20"
        :x2="center"
        :y2="size - 20"
        stroke="black"
        stroke-width="2"
    />

    <circle
        v-for="point in props.points"
        :key="point.id"
        :cx="toSvgX(point.x)"
        :cy="toSvgY(point.y)"
        r="5"
        :fill="point.hit ? 'green' : 'red'"
    />

    <!-- Стрелка X -->
    <polyline
        :points="`
    ${size - 30},${center - 6}
    ${size - 20},${center}
    ${size - 30},${center + 6}
  `"
        fill="none"
        stroke="black"
        stroke-width="2"
    />

    <!-- Стрелка Y -->
    <polyline
        :points="`
    ${center - 6},30
    ${center},20
    ${center + 6},30
  `"
        fill="none"
        stroke="black"
        stroke-width="2"
    />

    <g
        v-for="tick in ticks"
        :key="`x-${tick}`"
    >
      <line
          :x1="toSvgX(tick)"
          :x2="toSvgX(tick)"
          :y1="center - 5"
          :y2="center + 5"
          stroke="black"
      />

      <text
          :x="toSvgX(tick)"
          :y="center + 20"
          text-anchor="middle"
          font-size="12"
      >
        {{ tick }}
      </text>
    </g>

    <g
        v-for="tick in ticks"
        :key="`y-${tick}`"
    >
      <line
          :x1="center - 5"
          :x2="center + 5"
          :y1="toSvgY(tick)"
          :y2="toSvgY(tick)"
          stroke="black"
      />

      <text
          :x="center + 12"
          :y="toSvgY(tick) + 4"
          font-size="12"
      >
        {{ tick }}
      </text>
    </g>

    <text
        :x="size - 15"
        :y="center - 10"
        font-size="14"
    >
      X
    </text>

    <text
        :x="center + 10"
        y="15"
        font-size="14"
    >
      Y
    </text>

    <text
        :x="center + 8"
        :y="center + 16"
        font-size="12"
    >
      0
    </text>

    <text
        x="20"
        y="30"
    >
      R = {{ props.r }}
    </text>
  </svg>



</template>

<style scoped>
.coordinate-plane {
  width: min(100%, 420px);
  height: auto;
  padding: 10px;
  background:
    linear-gradient(#eef2ff 1px, transparent 1px),
    linear-gradient(90deg, #eef2ff 1px, transparent 1px),
    #fff;
  background-size: 50px 50px;
  border: 1px solid #dbe3ef;
  border-radius: 12px;
  cursor: crosshair;
}

.coordinate-plane line,
.coordinate-plane polyline {
  stroke: #334155;
}

.coordinate-plane text {
  fill: #475569;
  font-weight: 700;
  user-select: none;
}

.coordinate-plane circle {
  stroke: #fff;
  stroke-width: 2;
}
</style>
