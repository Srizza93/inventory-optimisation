import { describe, it, expect, vi } from 'vitest'
import { flushPromises, shallowMount } from '@vue/test-utils'
import OptimizationView from '@/views/OptimizationView.vue'

vi.mock('@/api/optimizationApi', () => ({
  getOptimizationSchedule: vi.fn(() =>
    Promise.resolve({
      inventoryParameters: {
        id: '3d18d976-4b65-42ac-8c22-4b4de334bbdb',
        deliveryDelay: 3,
        packageFormat: 2,
        workingDaysConsumption: 3,
        weekendConsumption: 4,
        purchaseDay: 'SUNDAY',
        currentStock: 6,
      },
      purchaseSchedule: [
        {
          purchaseDate: '05-01-2025',
          orderAmount: 34,
          currentStock: 2,
        },
        {
          purchaseDate: '06-01-2025',
          orderAmount: 0,
          currentStock: 0,
        },
        {
          purchaseDate: '07-01-2025',
          orderAmount: 0,
          currentStock: 0,
        },
      ],
    }),
  ),
}))

describe('Optimization view', () => {
  it('Passes props to the Line chart', async () => {
    const wrapper = shallowMount(OptimizationView)

    await flushPromises()

    const lineChart = wrapper.findComponent({ name: 'Line' })

    expect(lineChart.exists()).toBeTruthy()
    expect(lineChart.props('data')).toEqual({
      datasets: [
        {
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1,
          data: [2, 0, 0],
          label: 'Current Stock',
        },
      ],
      labels: ['05-01-2025', '06-01-2025', '07-01-2025'],
    })
  })
})
