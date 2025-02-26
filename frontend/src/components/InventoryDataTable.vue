<template>
  <SkeletonCard v-if="pending" :lines="15" />

  <template v-else>
    <div class="pagination">
      <button
        @click="$emit('previous-page')"
        :disabled="currentPage === 1"
        class="pagination__button"
      >
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
      <button
        @click="$emit('next-page')"
        :disabled="currentPage === totalPages"
        class="pagination__button"
        data-testid="next-page"
      >
        {{ $t('optimization_inventory-parameters_purchase-schedule_next--label') }}
      </button>
    </div>

    <table
      class="table"
      style="table-layout: fixed; border-collapse: collapse"
      data-testid="purchase-schedule-table"
    >
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
  </template>
</template>

<script lang="ts" setup>
import type { PurchaseSchedule } from '@/types/PurchaseSchedule'

import SkeletonCard from '@/components/SkeletonCard.vue'

interface Header {
  title: string
  value: string
}

defineProps<{
  headers: Header[]
  items: PurchaseSchedule[]
  pending: boolean
  currentPage: number
  totalPages: number
  paginatedItems: PurchaseSchedule[]
}>()

defineEmits(['previous-page', 'next-page'])
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
  margin: 15px 0;
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
