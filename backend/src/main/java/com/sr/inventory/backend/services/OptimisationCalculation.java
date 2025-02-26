package com.sr.inventory.backend.services;

import com.sr.inventory.backend.model.DeliveryOrder;
import com.sr.inventory.backend.controller.dto.PurchaseSchedule;
import com.sr.inventory.backend.model.InventoryParameters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OptimisationCalculation {

    LocalDate startDate = LocalDate.of(2025, 1, 5);
    LocalDate endDate = startDate.plusYears(1);

    private static final List<String> DAYS = List.of(
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY",
            "SUNDAY"
    );

    public List<PurchaseSchedule> calculateOptimisation(InventoryParameters inventoryParameters) {

        var actualDate = startDate;
        List<PurchaseSchedule> purchaseSchedule = new ArrayList<>();
        List<DeliveryOrder> deliveryOrders = new ArrayList<>();
        var currentStock = inventoryParameters.getCurrentStock();
        var packageFormat = inventoryParameters.getPackageFormat();
        var bufferStock = getBufferStock(inventoryParameters);

        while (actualDate.isBefore(endDate)) {
            var dailyConsumption = getDailyConsumption(inventoryParameters, actualDate, currentStock);

            currentStock = currentStock - dailyConsumption;

            var orderAmount = getQuantityToBuy(inventoryParameters, actualDate, packageFormat, currentStock, bufferStock, deliveryOrders);

            if (orderAmount > 0) {
                deliveryOrders.add(DeliveryOrder.builder()
                        .deliveryDate(actualDate.plusDays(inventoryParameters.getDeliveryDelay()))
                        .orderAmount(orderAmount)
                        .build());
            }

            if (!deliveryOrders.isEmpty()) {
                var iterator = deliveryOrders.iterator();
                while (iterator.hasNext()) {
                    var deliveryOrder = iterator.next();
                    if (actualDate.isEqual(deliveryOrder.getDeliveryDate())) {
                        currentStock += deliveryOrder.getOrderAmount();
                        iterator.remove();
                    }
                }
            }

            purchaseSchedule.add(PurchaseSchedule.builder()
                    .currentStock(currentStock)
                    .purchaseDate(actualDate)
                    .orderAmount(orderAmount)
                    .build());

            actualDate = actualDate.plusDays(1);
        }

        return purchaseSchedule;
    }

    private Integer getQuantityToBuy(InventoryParameters inventoryParameters, LocalDate actualDate,
                                     Integer packageFormat, Integer currentStock, Integer bufferStock,
                                     List<DeliveryOrder> deliveryOrders) {
        var actualDayOfWeek = actualDate.getDayOfWeek().toString();

        if (actualDayOfWeek.equals(inventoryParameters.getPurchaseDay())) {
            var weekRequiredConsumption = (5 * inventoryParameters.getWorkingDaysConsumption()) + (2 * inventoryParameters.getWeekendConsumption()) - currentStock;

            var pendingDeliveries = deliveryOrders.stream()
                    .filter(order -> order.getDeliveryDate().isAfter(actualDate) && order.getDeliveryDate().isBefore(actualDate.plusDays(inventoryParameters.getDeliveryDelay() + 1)))
                    .mapToInt(DeliveryOrder::getOrderAmount)
                    .sum();

            weekRequiredConsumption -= pendingDeliveries;

            if (weekRequiredConsumption % packageFormat == 0) {
                return weekRequiredConsumption + bufferStock;
            }
            var roundedUpWeeklyConsumption = (int) Math.ceil((double) weekRequiredConsumption / packageFormat) * packageFormat;

            return roundedUpWeeklyConsumption + bufferStock;
        }
        return 0;
    }

    private Integer getDailyConsumption(InventoryParameters inventoryParameters, LocalDate actualDate, Integer currentStock) {
        var consumption = 0;

        if (actualDate.getDayOfWeek().getValue() < 6) {
            consumption = inventoryParameters.getWorkingDaysConsumption();
        } else {
            consumption = inventoryParameters.getWeekendConsumption();
        }

        if (currentStock <= consumption) {
            consumption = currentStock;
        }

        return consumption;
    }

    private Integer getBufferStock(InventoryParameters inventoryParameters) {
        var maxDailyConsumption = Math.max(inventoryParameters.getWorkingDaysConsumption(), inventoryParameters.getWeekendConsumption());
        return maxDailyConsumption * inventoryParameters.getDeliveryDelay();
    }

    public void validateParameters(InventoryParameters inventoryParameters) {
        Optional.ofNullable(inventoryParameters.getDeliveryDelay())
                .filter(d -> d >= 0)
                .orElseThrow(() -> new IllegalArgumentException("Invalid delivery delay"));

        Optional.ofNullable(inventoryParameters.getWeekendConsumption())
                .filter(c -> c >= 0)
                .orElseThrow(() -> new IllegalArgumentException("Invalid weekend consumption"));

        Optional.ofNullable(inventoryParameters.getWorkingDaysConsumption())
                .filter(c -> c >= 0)
                .orElseThrow(() -> new IllegalArgumentException("Invalid working days consumption"));

        Optional.ofNullable(inventoryParameters.getCurrentStock())
                .orElseThrow(() -> new IllegalArgumentException("Current stock must not be null"));

        Optional.ofNullable(inventoryParameters.getPackageFormat())
                .filter(p -> p > 0)
                .orElseThrow(() -> new IllegalArgumentException("Invalid package format"));

        Optional.ofNullable(inventoryParameters.getPurchaseDay())
                .filter(DAYS::contains)
                        .orElseThrow(() -> new IllegalArgumentException("Purchase day is required"));
    }


}
