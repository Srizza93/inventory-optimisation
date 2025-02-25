<script setup lang="ts">
import { computed, type ComputedRef } from 'vue'
import { notification } from '@/stores/StateNotificationService'

const errorMessage: ComputedRef<string> = computed(() => {
  return notification.notification?.message
    ? notification.notification.message
    : 'An error has occured'
})

const closeError = (): void => {
  notification.hideNotification()
}
</script>

<template>
  <div
    v-if="notification.notification.show"
    class="toast fixed w-3/6 bottom-8 z-50"
    :data-testid="notification.notification.type.CONTAINER_ID"
  >
    <div
      data-test-id="main-container"
      :id="notification.notification.type.CONTAINER_ID"
      :class="[
        notification.notification.type.BG_COLOR,
        notification.notification.type.BORDER_COLOR,
      ]"
      class="flex items-center w-full bZ p-4 mb-4 text-white rounded-lg shadow dark:text-white-400 overflow-hidden h-16 shadow-2xl border-2"
      role="alert"
    >
      <div
        data-test-id="image"
        :class="notification.notification.type.DARK_BG_COLOR_CLASS"
        class="inline-flex items-center justify-center flex-shrink-0 w-8 h-8 rounded-lg"
      >
        <svg
          class="w-5 h-5"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          fill="currentColor"
          viewBox="0 0 20 20"
        >
          <path
            d="M10 .5a9.5 9.5 0 1 0 9.5 9.5A9.51 9.51 0 0 0 10 .5Zm3.707 8.207-4 4a1 1 0 0 1-1.414 0l-2-2a1 1 0 0 1 1.414-1.414L9 10.586l3.293-3.293a1 1 0 0 1 1.414 1.414Z"
          />
        </svg>
        <span class="sr-only">Check icon</span>
      </div>
      <div
        data-test-id="text-message"
        class="ml-3 mr-3 text-sm font-normal overflow-hidden text-ellipsis whitespace-nowrap"
      >
        {{ errorMessage }}
      </div>
      <button
        data-test-id="close-button"
        type="button"
        :class="[notification.notification.type.BG_BUTTON]"
        class="ms-auto -mx-1.5 -my-1.5 text-white-400 hover:text-white-900 rounded-lg focus:ring-2 focus:ring-white-300 p-1.5 hover:bg-white-100 inline-flex items-center justify-center h-8 w-8 dark:text-white-500 dark:hover:text-white dark:hover:bg-white-700"
        :data-dismiss-target="notification.notification.type.CONTAINER_ID"
        aria-label="Close"
        @click="closeError"
      >
        <span class="sr-only">Close</span>
        <svg
          class="w-3 h-3"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          viewBox="0 0 14 14"
        >
          <path
            stroke="currentColor"
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"
          />
        </svg>
      </button>
    </div>
  </div>
</template>

<style scoped>
.toast {
  left: calc(25% + 50px);
}

.border-danger {
  border-color: #e3342f;
}
</style>
