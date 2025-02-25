<template>
  <div class="toaster-container">
    <div v-if="notification.notification.show" :class="toastClass" class="toaster" role="alert">
      <span>{{ errorMessage }}</span>
      <button class="toaster__close-button" @click="notification.hideNotification">×</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { notification } from '@/stores/StateNotificationService'
import { ToastType } from '@/types/NotificationState'

const errorMessage = computed(() => notification.notification?.message || 'An error has occurred')

const toastClass = computed(() => {
  switch (notification.notification.type) {
    case ToastType.DANGER_TOAST:
      return 'danger'
    case ToastType.SUCCESS_TOAST:
      return 'success'
    case ToastType.WARNING_TOAST:
      return 'warning'
    default:
      return 'danger'
  }
})
</script>

<style scoped lang="scss">
.toaster-container {
  position: relative;
}

.toaster {
  position: fixed;
  top: 20px; /* Adjust as needed */
  left: 50%;
  width: 80%;
  transform: translateX(-50%);
  padding: 20px 25px;
  border-radius: 5px;
  overflow: hidden;
  z-index: 1000;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);

  &__close-button {
    position: absolute;
    top: 50%;
    right: 10px;
    transform: translateY(-50%);
    border: none;
    background: none;
    cursor: pointer;
    color: #fff;
    font-size: 18px;
  }
}

.danger {
  background-color: #e3352feb;
}
.success {
  background-color: #38a169e7;
}
.warning {
  background-color: #ffcc00e4;
}

.toast-enter-active,
.toast-leave-active {
  opacity: 1;
  transform: translateY(0);
}

.toast-enter,
.toast-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}
</style>
