<template>
  <div class="skeleton-card">
    <div class="skeleton-card__content">
      <div v-for="line in lines" class="skeleton-card__line"></div>
    </div>
  </div>
</template>

<script setup lang="ts">
defineProps<{
  lines: number
}>()
</script>

<style scoped lang="scss">
.skeleton-card {
  background-color: #fff;
  border-radius: 5px;
  overflow: hidden;
  margin-bottom: 20px;
  padding: 20px 0;
  width: 100%;

  &__content {
    display: grid;
    gap: 20px;
  }

  &__line {
    background-color: #f6f6f6;
    border-radius: 5px;
    height: 20px;
    animation: loading 1s infinite;
    background: linear-gradient(90deg, #f6f6f6 25%, #e0e0e0 50%, #f6f6f6 75%);
    background-size: 200% 100%;
  }
}

@keyframes loading {
  0% {
    background-position: 100% 0;
  }
  100% {
    background-position: 0 0;
  }
}
</style>
