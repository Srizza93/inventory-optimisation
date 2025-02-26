<template>
  <SkeletonCard v-if="pending || !newParameters" :lines="2" />
  <form v-else class="parameters" data-testid="parameters-form">
    <div class="form-group">
      <label for="current-stock">{{
        $t('optimization_inventory-parameters_current-stock--label')
      }}</label>
      <input
        type="number"
        class="form-group__input"
        id="current-stock"
        data-testid="current-stock-input"
        aria-describedby="current-stock-help"
        v-model="newParameters.currentStock"
      />
    </div>
    <div class="form-group">
      <label for="delivery-delay">{{
        $t('optimization_inventory-parameters_delivery-day--label')
      }}</label>
      <input
        type="number"
        class="form-group__input"
        min="0"
        id="delivery-delay"
        aria-describedby="delivery-delay-help"
        v-model="newParameters.deliveryDelay"
      />
    </div>
    <div class="form-group">
      <label for="package-format">{{
        $t('optimization_inventory-parameters_package-format--label')
      }}</label>
      <input
        type="number"
        class="form-group__input"
        id="package-format"
        aria-describedby="package-format-help"
        min="1"
        v-model="newParameters.packageFormat"
      />
    </div>
    <div class="form-group">
      <label for="purchase-day">{{
        $t('optimization_inventory-parameters_purchase-day--label')
      }}</label>
      <select
        class="form-group__input"
        id="purchase-day"
        aria-describedby="purchase-day-help"
        v-model="newParameters.purchaseDay"
      >
        <option v-for="day in daysOptions" :key="day.option" :value="day.option">
          {{ day.value }}
        </option>
      </select>
    </div>
    <div class="form-group">
      <label for="weekend-consumption">{{
        $t('optimization_inventory-parameters_weekend-consumption--label')
      }}</label>
      <input
        type="number"
        class="form-group__input"
        id="weekend-consumption"
        aria-describedby="weekend-consumption-help"
        min="0"
        v-model="newParameters.weekendConsumption"
      />
    </div>
    <div class="form-group">
      <label for="working-days-consumption">{{
        $t('optimization_inventory-parameters_working-days-consumption--label')
      }}</label>
      <input
        type="number"
        class="form-group__input"
        id="working-days-consumption"
        min="0"
        aria-describedby="working-days-consumption-help"
        v-model="newParameters.workingDaysConsumption"
      />
    </div>
  </form>
  <button
    class="update-optimization"
    :class="{ 'update-optimization--disabled': pending }"
    data-testid="update-optimization-button"
    @click="updateParameters"
    @keydown.enter.prevent="updateParameters"
    :disabled="pending"
    :aria-disabled="pending"
  >
    {{ $t('optimization_inventory-parameters_update-button--label') }}
  </button>
</template>

<script setup lang="ts">
import type { InventoryParameters } from '@/types/PurchaseSchedule'
import { computed, onMounted, ref, type Ref } from 'vue'

import SkeletonCard from './SkeletonCard.vue'
import { days } from '@/utils/daysHelper'
import i18n from '@/i18n'

const props = defineProps<{ parameters: InventoryParameters; pending: boolean }>()
const emit = defineEmits(['update:parameters'])

const daysOptions = computed(() =>
  days.map((d) => ({
    option: d,
    value: i18n.global.t('optimization_global_days_' + d.toLowerCase() + '--label'),
  })),
)

const newParameters: Ref<InventoryParameters | null> = ref(null)

function updateParameters() {
  emit('update:parameters', newParameters.value)
}

function initForm() {
  newParameters.value = { ...props.parameters }
}

onMounted(() => {
  initForm()
})
</script>

<style lang="scss">
.parameters {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 15px;
  margin-top: 10px;
}

.form-group {
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;

  &__input {
    padding: 0.5rem;
    border: 1px solid #ccc;
    border-radius: 5px;
  }
}

.update-optimization {
  background-color: #183247;
  color: white;
  padding: 10px 20px;
  margin-bottom: 15px;
  border-radius: 5px;
  border: none;
  font-weight: bold;
  cursor: pointer;

  &:hover {
    background-color: #0f1f2f;
  }

  &--disabled {
    background-color: #ccc;
    cursor: not-allowed;

    &:hover {
      background-color: #ccc;
    }
  }
}
</style>
