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
        var purchasedQuantity = 0;
        var deliveryDay = DayOfWeek.valueOf(inventoryParameters.getPurchaseDay())
                .plus(inventoryParameters.getDeliveryDelay())
                .getValue();
        var bufferStock = getBufferStock(inventoryParameters);

        while (actualDate.isBefore(endDate)) {
            var quantityToBuy = getQuantityToBuy(inventoryParameters, actualDate, packageFormat, currentStock, bufferStock);
            var dailyConsumption = getDailyConsumption(inventoryParameters, actualDate);

            if (quantityToBuy > 0) {
                purchasedQuantity = quantityToBuy;
            }

            currentStock = updateStock(currentStock, actualDate, purchasedQuantity, dailyConsumption, deliveryDay);

            if (currentStock < 0) {
                currentStock = inventoryParameters.getCurrentStock();
                packageFormat = packageFormat + inventoryParameters.getPackageFormat();
                purchaseSchedule.clear();
                actualDate = startDate;
            } else {
                purchaseSchedule.add(PurchaseSchedule.builder()
                                .currentStock(currentStock)
                                .purchaseDate(actualDate)
                                .quantityToBuy(quantityToBuy)
                        .build());
            }

            actualDate = actualDate.plusDays(1);
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

    private Integer updateStock(Integer currentStock, LocalDate actualDate, Integer purchasedQuantity,
                              Integer dailyConsumption, Integer deliveryDay) {

        if (actualDate.getDayOfWeek().getValue() == deliveryDay) {
            return currentStock + purchasedQuantity - dailyConsumption;
        }
        return currentStock - dailyConsumption;
    }

    private Integer getDailyConsumption(InventoryParameters inventoryParameters, LocalDate actualDate) {
        if (actualDate.getDayOfWeek().getValue() < 5) {
            return inventoryParameters.getWorkingDaysConsumption();
        } else {
            return inventoryParameters.getWeekendConsumption();
        }
    }

    private Integer getBufferStock(InventoryParameters inventoryParameters) {
        var maxDailyConsumption = Math.max(inventoryParameters.getWorkingDaysConsumption(), inventoryParameters.getWeekendConsumption());
        return maxDailyConsumption * inventoryParameters.getDeliveryDelay();
    }

}
