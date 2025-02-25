import { reactive } from 'vue'
import type { NotificationState } from '../types/NotificationState'
import { ToastType } from '../types/NotificationState'

const defaultMessage = 'An error has occured'

export const notification = reactive({
  notification: {
    show: false,
    message: '',
    type: ToastType.SUCCESS_TOAST,
  } as NotificationState,

  sendNotification(typeEnum: ToastType, msg?: string) {
    this.notification = {
      show: true,
      message: msg || defaultMessage,
      type: typeEnum,
    }
    setTimeout(() => this.hideNotification(), 50000)
  },

  hideNotification() {
    this.notification = {
      message: '',
      type: ToastType.SUCCESS_TOAST,
      show: false,
    }
  },
})
