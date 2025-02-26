<template>
  <ParametersForm
    v-if="purchaseScheduleData"
    :parameters="purchaseScheduleData.inventoryParameters"
    :pending="pending"
    @update:parameters="updateParameters"
  />
  <InventoryDataTable
    v-if="purchaseScheduleData"
    :headers="purchaseScheduleHeaders"
    :items="purchaseScheduleData.purchaseSchedule"
    :pending="pending"
  />
</template>

<script setup lang="ts">
import { onMounted, ref, type Ref } from 'vue'
import i18n from '@/i18n'

import { getOptimizationSchedule, putOptimizationSchedule } from '@/api/optimizationApi'
import { notification } from '@/stores/StateNotificationService'
import InventoryDataTable from '@/components/InventoryDataTable.vue'
import type { InventoryParameters, PurchaseScheduleItem } from '@/types/PurchaseSchedule'
import { ToastType } from '@/types/NotificationState'
import ParametersForm from '@/components/ParametersForm.vue'

const pending = ref(false)
const purchaseScheduleData: Ref<PurchaseScheduleItem | null> = ref(null)

const purchaseScheduleHeaders = [
  {
    title: i18n.global.t('optimization_purchase-schedule_purchase-date--title'),
    value: 'purchaseDate',
  },
  {
    title: i18n.global.t('optimization_purchase-schedule_order-amount--title'),
    value: 'orderAmount',
  },
  {
    title: i18n.global.t('optimization_purchase-schedule_current-stock--title'),
    value: 'currentStock',
  },
]

function updateParameters(parameters: InventoryParameters) {
  if (!purchaseScheduleData.value) {
    return
  }

  purchaseScheduleData.value.inventoryParameters = parameters

  updateData()
}

function updateData() {
  pending.value = true

  if (!purchaseScheduleData.value?.inventoryParameters) {
    notification.sendNotification(ToastType.DANGER_TOAST, 'No data to update')
    pending.value = false
    return
  }

  putOptimizationSchedule(purchaseScheduleData.value.inventoryParameters)
    .then((response) => {
      purchaseScheduleData.value = response
      notification.sendNotification(ToastType.SUCCESS_TOAST, 'Data updated successfully')
    })
    .catch((error: unknown) => {
      const err = error as { response?: { statusText?: string } }
      notification.sendNotification(
        ToastType.DANGER_TOAST,
        err.response?.statusText || 'An error occurred',
      )
    })
    .finally(() => {
      pending.value = false
    })
}

function initData() {
  pending.value = true

  getOptimizationSchedule('3d18d976-4b65-42ac-8c22-4b4de334bbdb')
    .then((response) => {
      purchaseScheduleData.value = response
    })
    .catch((error) => {
      const err = error as { response?: { statusText?: string } }
      notification.sendNotification(
        ToastType.DANGER_TOAST,
        error.response?.statusText || 'An error occurred',
      )
    })
    .finally(() => {
      pending.value = false
    })
}

onMounted(() => {
  initData()
})
</script>
