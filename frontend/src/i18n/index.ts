import { createI18n } from 'vue-i18n'

import frFR from './fr_FR.json'
import enEN from './en_EN.json'

export default createI18n({
  locale: 'fr',
  fallbackLocale: 'fr',
  silentTranslationWarn: true,
  messages: {
    fr: frFR,
    en: enEN,
  } as Record<string, Record<string, string>>,
  silentFallbackWarn: true,
  globalInjection: true,
  warnHtmlInMessage: 'off',
})
