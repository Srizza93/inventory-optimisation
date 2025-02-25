import { reactive } from 'vue'
import type { NotificationState } from '../types/NotificationState'
import { SuccessToast, getToastEnum, ToastType } from '../types/NotificationState'

const defaultMessage = 'An error has occured'

export const notification = reactive({
  notification: {
    show: false,
    message: '',
    type: SuccessToast,
  } as NotificationState,

  sendNotification(typeEnum: ToastType, msg?: string) {
    this.notification = {
      show: true,
      message: msg || defaultMessage,
      type: getToastEnum(typeEnum),
    }
    setTimeout(() => this.hideNotification(), 5000)
  },

  hideNotification() {
    this.notification = {
      ...this.notification,
      show: false,
    }
  },
})
