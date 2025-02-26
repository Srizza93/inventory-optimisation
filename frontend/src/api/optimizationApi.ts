import type { InventoryParameters, PurchaseScheduleItem } from '@/types/PurchaseSchedule'
import axios from 'axios'

const URL = import.meta.env.VITE_API_URL
const parametersId = import.meta.env.VITE_APP_PARAMETERS_ID

export async function getOptimizationSchedule(): Promise<PurchaseScheduleItem> {
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
