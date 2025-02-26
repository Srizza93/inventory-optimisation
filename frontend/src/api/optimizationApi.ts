import type { InventoryParameters, PurchaseScheduleItem } from '@/types/PurchaseSchedule'
import axios from 'axios'

const URL = import.meta.env.VITE_API_URL

export async function getOptimizationSchedule(parametersId: string): Promise<PurchaseScheduleItem> {
  return await axios
    .get(`${URL}/inventory`, {
      params: {
        parametersId,
      },
    })
    .then((response) => response.data)
}

export async function putOptimizationSchedule(
  inventoryParameters: InventoryParameters,
): Promise<PurchaseScheduleItem> {
  return await axios.put(`${URL}/inventory`, inventoryParameters).then((response) => response.data)
}
