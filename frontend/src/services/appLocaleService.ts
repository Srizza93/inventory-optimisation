interface ImportMetaEnv {
  PROD: boolean
}

declare global {
  interface Window {
    test?: any
  }
}

const fallbackLocale = 'en-EN'

export default class AppLocaleService {
  static getAppLocale() {
    if ((window as Window).test) {
      return fallbackLocale
    }
    return (
      window.navigator?.language ||
      document.querySelector('html')!.getAttribute('lang') ||
      fallbackLocale
    )
  }
}
