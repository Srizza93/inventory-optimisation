export interface NotificationState {
  show: boolean
  message?: string
  type: typeof SuccessToast | typeof DangerToast | typeof WarningToast
}

export enum SuccessToast {
  CONTAINER_ID = '#toast-success',
  BG_COLOR = 'bg-success-opacity',
  BG_BUTTON = 'bg-success',
  BORDER_COLOR = 'border-success',
  DARK_BG_COLOR_CLASS = 'dark:bg-green-800'
}

export enum DangerToast {
  CONTAINER_ID = '#toast-danger',
  BG_COLOR = 'bg-danger-opacity',
  BG_BUTTON = 'bg-danger',
  BORDER_COLOR = 'border-danger',
  DARK_BG_COLOR_CLASS = 'dark:bg-red-800'
}

export enum WarningToast {
  CONTAINER_ID = '#toast-warning',
  BG_COLOR = 'bg-warning-opacity',
  BG_BUTTON = 'bg-warning',
  BORDER_COLOR = 'border-warning',
  DARK_BG_COLOR_CLASS = 'dark:bg-orange-700'
}

export enum ToastType {
  SUCCESS_TOAST = 'SuccessToast',
  DANGER_TOAST = 'DangerToast',
  WARNING_TOAST = 'WarningToast'
}

export function getToastEnum(
  enumType: ToastType
): typeof SuccessToast | typeof DangerToast | typeof WarningToast {
  switch (enumType) {
    case ToastType.SUCCESS_TOAST:
      return SuccessToast
    case ToastType.DANGER_TOAST:
      return DangerToast
    case ToastType.WARNING_TOAST:
      return WarningToast
    default:
      return SuccessToast
  }
}
