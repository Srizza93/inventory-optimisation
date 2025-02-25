<template>
  <form>
    <div class="form-group">
      <label for="current-stock">{{
        $t('optimization_inventory-parameters-current-stock--label')
      }}</label>
      <input
        type="number"
        class="form-control"
        id="current-stock"
        :value="parameters.currentStock"
      />
    </div>
    <div class="form-group">
      <label for="delivery-delay">{{
        $t('optimization_inventory-parameters-delivery-day--label')
      }}</label>
      <input
        type="number"
        class="form-control"
        min="0"
        id="delivery-delay"
        :value="parameters.deliveryDelay"
      />
    </div>
    <div class="form-group">
      <label for="package-format">{{
        $t('optimization_inventory-parameters-package-format--label')
      }}</label>
      <input
        type="number"
        class="form-control"
        id="package-format"
        min="0"
        :value="parameters.packageFormat"
      />
    </div>
    <div class="form-group">
      <label for="purchase-day">{{
        $t('optimization_inventory-parameters-purchase-day--label')
      }}</label>
      <input type="text" class="form-control" id="purchase-day" :value="parameters.purchaseDay" />
    </div>
    <div class="form-group">
      <label for="weekend-consumption">{{
        $t('optimization_inventory-parameters-weekend-consumption--label')
      }}</label>
      <input
        type="number"
        class="form-control"
        id="weekend-consumption"
        min="0"
        :value="parameters.weekendConsumption"
      />
    </div>
    <div class="form-group">
      <label for="working-days-consumption">{{
        $t('optimization_inventory-parameters-working-days-consumption--label')
      }}</label>
      <input
        type="number"
        class="form-control"
        id="working-days-consumption"
        min="0"
        :value="parameters.workingDaysConsumption"
      />
    </div>
  </form>
</template>

<script setup lang="ts">
import type { InventoryParameters } from '@/types/PurchaseSchedule'

defineProps<{
  parameters: InventoryParameters
}>()
</script>

<style lang="scss"></style>
