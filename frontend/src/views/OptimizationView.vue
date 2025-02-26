<template>
  <ParametersForm
    v-if="purchaseScheduleData"
    :parameters="purchaseScheduleData.inventoryParameters"
    :pending="pending"
    @update:parameters="updateParameters"
  />
  <div>
    <p id="chart-description" class="sr-only">
      {{ $t('optimization_chart_description') }}
    </p>
    <Line
      v-if="purchaseScheduleData"
      :options="chartOptions"
      :data="chartData"
      class="line-chart"
      id="optimization-chart"
      tabindex="0"
      role="img"
      aria-labelledby="chart-title chart-description"
    />
  </div>
  <InventoryDataTable
    v-if="paginatedItems && purchaseSchedule"
    :headers="purchaseScheduleHeaders"
    :items="purchaseSchedule"
    :pending="pending"
    :totalPages="totalPages"
    :currentPage="currentPage"
    :paginatedItems="paginatedItems"
    @previous-page="goToPreviousPage"
    @next-page="goToNextPage"
  />
</template>

<script setup lang="ts">
import { computed, onMounted, ref, type Ref } from 'vue'
import i18n from '@/i18n'

import { getOptimizationSchedule, putOptimizationSchedule } from '@/api/optimizationApi'
import { notification } from '@/stores/StateNotificationService'
import InventoryDataTable from '@/components/InventoryDataTable.vue'
import type { InventoryParameters, PurchaseScheduleItem } from '@/types/PurchaseSchedule'
import { ToastType } from '@/types/NotificationState'
import ParametersForm from '@/components/ParametersForm.vue'
import { Line } from 'vue-chartjs'
import { Chart as ChartJS, registerables } from 'chart.js'
import { formatDate } from '@/utils/dateHelper'

ChartJS.register(...registerables)

const pending = ref(false)
const purchaseScheduleData: Ref<PurchaseScheduleItem | null> = ref(null)

const purchaseSchedule = computed(
  () =>
    purchaseScheduleData.value?.purchaseSchedule.map((schedule) => ({
      ...schedule,
      purchaseDate: formatDate(schedule.purchaseDate),
    })) || null,
)

const itemsPerPage = 15
const currentPage = ref(1)

const totalPages = computed(() =>
  Math.ceil((purchaseScheduleData.value?.purchaseSchedule?.length || 0) / itemsPerPage),
)

const startPage = computed(() => (currentPage.value - 1) * itemsPerPage)

const paginatedItems = computed(() =>
  purchaseSchedule.value?.slice(startPage.value, startPage.value + itemsPerPage),
)

function goToNextPage() {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

function goToPreviousPage() {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

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

const chartOptions = {
  responsive: true,
  maintainAspectRatio: false,
}

const chartData = computed(() => ({
  labels:
    purchaseScheduleData.value?.purchaseSchedule
      .slice(startPage.value, startPage.value + itemsPerPage)
      .map((item) => item.purchaseDate) || [],
  datasets: [
    {
      label: i18n.global.t('optimization_purchase-schedule_current-stock--title'),
      data:
        purchaseScheduleData.value?.purchaseSchedule
          .slice(startPage.value, startPage.value + itemsPerPage)
          .map((item) => item.currentStock) || [],
      backgroundColor: 'rgba(255, 99, 132, 0.2)',
      borderColor: 'rgba(255, 99, 132, 1)',
      borderWidth: 1,
    },
  ],
}))

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

  getOptimizationSchedule()
    .then((response) => {
      purchaseScheduleData.value = response
    })
    .catch((error) => {
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

onMounted(() => {
  initData()
})
</script>

<style scoped lang="scss">
.line-chart {
  height: 100%;
  max-height: 400px;
  margin-bottom: 15px;
}
</style>
