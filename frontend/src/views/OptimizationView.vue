<template>
  <div>
    <h1>Optimization</h1>
    <p>Optimization view</p>
  </div>
  <InventoryDataTable
    v-if="purchaseScheduleData"
    :headers="purchaseScheduleHeaders"
    :items="purchaseScheduleData.purchaseSchedule"
  />
</template>

<script setup lang="ts">
import { onMounted, ref, type Ref } from 'vue'
import i18n from '@/i18n'

import { getOptimizationSchedule } from '@/api/optimizationApi'
import { notification } from '@/stores/StateNotificationService'
import InventoryDataTable from '@/components/InventoryDataTable.vue'
import type { PurchaseScheduleItem } from '@/types/PurchaseSchedule'
import { ToastType } from '@/types/NotificationState'

let pending = ref(false)
let purchaseScheduleData: Ref<PurchaseScheduleItem | null> = ref(null)

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

function initData() {
  pending.value = true

  getOptimizationSchedule('3d18d976-4b65-42ac-8c22-4b4de334bbdb')
    .then((response) => {
      purchaseScheduleData.value = response
    })
    .catch((error) => {
      console.log(error)
      notification.sendNotification(ToastType.DANGER_TOAST, error.response?.data?.message)
    })
    .finally(() => {
      pending.value = false
    })
}

onMounted(() => {
  initData()
})
</script>
