import i18n from '@/i18n'

export function formatDate(dateString: string): string {
  const [day, month, year] = dateString.split('-').map(Number)
  const date = new Date(year, month - 1, day)

  const formattedDate = new Intl.DateTimeFormat(i18n.global.locale, {
    weekday: 'long',
    day: 'numeric',
    month: 'long',
    year: 'numeric',
  }).format(date)

  return formattedDate.charAt(0).toUpperCase() + formattedDate.slice(1)
}
