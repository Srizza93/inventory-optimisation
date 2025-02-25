package com.sr.inventory.backend.business.service;

import com.sr.inventory.backend.dto.PurchaseSchedule;
import com.sr.inventory.backend.model.InventoryParameters;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OptimisationCalculation {

    LocalDate startDate = LocalDate.of(2025, 1, 5);
    LocalDate endDate = startDate.plusYears(1);

    public List<PurchaseSchedule> calculateOptimisation(InventoryParameters inventoryParameters) {

        var actualDate = startDate;
        List<PurchaseSchedule> purchaseSchedule = new ArrayList<>();
        var currentStock = inventoryParameters.getCurrentStock();
        var packageFormat = inventoryParameters.getPackageFormat();
        var bufferStock = getBufferStock(inventoryParameters);
        var deliveryDelayCounter = 0;
        var purchaseAmount = 0;

        while (actualDate.isBefore(endDate)) {
            var dailyConsumption = getDailyConsumption(inventoryParameters, actualDate, currentStock);
            var orderAmount = getQuantityToBuy(inventoryParameters, actualDate, packageFormat, currentStock, bufferStock);

            if (orderAmount > 0) {
                purchaseAmount = orderAmount;
            }

            if (deliveryDelayCounter == inventoryParameters.getDeliveryDelay()) {
                currentStock += purchaseAmount;
                purchaseAmount = 0;
                deliveryDelayCounter = 0;
            }

            currentStock = currentStock - dailyConsumption;

            purchaseSchedule.add(PurchaseSchedule.builder()
                    .currentStock(currentStock)
                    .purchaseDate(actualDate)
                    .orderAmount(orderAmount)
                    .build());

            actualDate = actualDate.plusDays(1);
            deliveryDelayCounter++;
        }

        return purchaseSchedule;
    }

    private Integer getQuantityToBuy(InventoryParameters inventoryParameters, LocalDate actualDate,
                                     Integer packageFormat, Integer currentStock, Integer bufferStock) {
        var actualDayOfWeek = actualDate.getDayOfWeek().toString();

        if (actualDayOfWeek.equals(inventoryParameters.getPurchaseDay())) {
            var weekRequiredConsumption = (5 * inventoryParameters.getWorkingDaysConsumption()) + (2 * inventoryParameters.getWeekendConsumption()) - currentStock;

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

        if (actualDate.getDayOfWeek().getValue() < 5) {
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

}
