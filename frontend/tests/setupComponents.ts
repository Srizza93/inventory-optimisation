import { config } from '@vue/test-utils'
import { vi } from 'vitest'
import { Line } from 'vue-chartjs'

config.global.mocks = {
  $t: vi.fn((t) => t),
}

config.global.components = {
  Line,
}
