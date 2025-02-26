export interface NotificationState {
  show: boolean
  message?: string
  type: ToastType
}

export enum ToastType {
  SUCCESS_TOAST = 'SuccessToast',
  DANGER_TOAST = 'DangerToast',
  WARNING_TOAST = 'WarningToast',
}
