<template>
  <table
    class="table-auto w-full text-center rounded-xl drop-shadow-lg"
    style="table-layout: fixed; border-collapse: collapse"
  >
    <thead class="w-full h-16">
      <tr class="header">
        <th scope="col" v-for="header in headers" :key="header.value">
          {{ header.title }}
        </th>
      </tr>
    </thead>
    <tbody>
      <tr class="items" v-for="(item, index) in items" :key="index + '-datatable'">
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

<script lang="ts" setup>
import type { PurchaseSchedule } from '@/types/PurchaseSchedule'

interface Header {
  title: string
  value: string
}

defineProps<{
  headers: Header[]
  items: PurchaseSchedule[]
}>()
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
</style>
