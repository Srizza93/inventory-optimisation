export interface PurchaseScheduleItem {
  inventoryParameters: InventoryParameters
  purchaseSchedule: PurchaseSchedule[]
}

export interface InventoryParameters {
  id: string
  deliveryDelay: number
  packageFormat: number
  workingDaysConsumption: number
  weekendConsumption: number
  purchaseDay: string
  currentStock: string
}

export interface PurchaseSchedule {
  purchaseDate: string
  orderAmount: number
  currentStock: number
}
