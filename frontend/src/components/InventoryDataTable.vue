<template>
  <SkeletonCard v-if="pending" :lines="15" />
  <table v-else class="table" style="table-layout: fixed; border-collapse: collapse">
    <thead>
      <tr class="header">
        <th class="header__title" scope="col" v-for="header in headers" :key="header.value">
          {{ header.title }}
        </th>
      </tr>
    </thead>
    <tbody>
      <tr class="items" v-for="(item, index) in paginatedItems" :key="index + '-datatable'">
        <td
          class="items__item"
          :data-testid="'table-item-' + index + '-' + header.value"
          v-for="header in headers"
          :key="header.value + '-' + index"
        >
          {{ item[header.value as keyof PurchaseSchedule] }}
        </td>
      </tr>
    </tbody>
  </table>

  <div class="pagination">
    <button @click="prevPage" :disabled="currentPage === 1" class="pagination__button">
      {{ $t('optimization_inventory-parameters_purchase-schedule_previous--label') }}
    </button>
    <span class="pagination__info">
      {{
        $t('optimization_inventory-parameters_purchase-schedule_pages--label', {
          currentPage,
          totalPages,
        })
      }}
    </span>
    <button @click="nextPage" :disabled="currentPage === totalPages" class="pagination__button">
      {{ $t('optimization_inventory-parameters_purchase-schedule_next--label') }}
    </button>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref } from 'vue'
import type { PurchaseSchedule } from '@/types/PurchaseSchedule'

import SkeletonCard from '@/components/SkeletonCard.vue'

interface Header {
  title: string
  value: string
}

const props = defineProps<{
  headers: Header[]
  items: PurchaseSchedule[]
  pending: boolean
}>()

const itemsPerPage = 15
const currentPage = ref(1)

const paginatedItems = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  return props.items.slice(start, start + itemsPerPage)
})

const totalPages = computed(() => Math.ceil(props.items.length / itemsPerPage))

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const prevPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}
</script>

<style lang="scss" scoped>
table {
  width: 100%;
  table-layout: fixed; /* Enforces consistent column widths */
  border-collapse: collapse; /* Ensures no gaps between rows/columns */
}

.header {
  background-color: #183247;
  color: #fff;

  &__title {
    padding: 10px 0;
  }
}

.items {
  &__item {
    text-align: center;
    padding: 10px;
  }

  &:nth-child(even) {
    background-color: #f6f6f6;
  }

  &:nth-child(odd) {
    background-color: #fff;
  }
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
  gap: 10px;
}

.pagination__button {
  background-color: #183247;
  color: white;
  padding: 8px 16px;
  border-radius: 5px;
  font-weight: bold;
  cursor: pointer;
  transition: 0.3s;

  &:hover {
    background-color: #0f1f2f;
  }

  &:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
}

.pagination__info {
  font-size: 14px;
  font-weight: bold;
}
</style>
