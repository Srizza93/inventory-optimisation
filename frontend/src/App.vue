<script setup lang="ts">
import { defineAsyncComponent } from 'vue'
import { RouterView } from 'vue-router'

const GlobalToast = defineAsyncComponent(() => import('@/components/GlobalToast.vue'))
</script>

<template>
  <GlobalToast />
  <RouterView />
</template>
