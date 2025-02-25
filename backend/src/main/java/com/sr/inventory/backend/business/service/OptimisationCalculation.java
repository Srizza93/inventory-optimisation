package com.sr.inventory.backend.business.service;

import com.sr.inventory.backend.dto.PurchaseSchedule;
import com.sr.inventory.backend.model.InventoryParameters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OptimisationCalculation {

    LocalDate startDate = LocalDate.of(2025, 1, 5); // Sunday, January 6, 2025
    LocalDate endDate = startDate.plusYears(1);

    public List<PurchaseSchedule> calculateOptimisation(InventoryParameters inventoryParameters) {

        var actualDate = startDate;
        List<PurchaseSchedule> purchaseSchedule = new ArrayList<>();
        var currentStock = inventoryParameters.getCurrentStock();
        var packageFormat = inventoryParameters.getPackageFormat();
        var bufferStock = getBufferStock(inventoryParameters);

        while (actualDate.isBefore(endDate)) {
            var quantityToBuy = getQuantityToBuyInfo(inventoryParameters, actualDate, packageFormat, currentStock);
            var dailyConsumption = getDailyConsumption(inventoryParameters, actualDate);

            currentStock = currentStock + quantityToBuy - dailyConsumption;


            // Out of stock check
            if (currentStock < 0) {
                currentStock = inventoryParameters.getCurrentStock();
                packageFormat = packageFormat + inventoryParameters.getPackageFormat();
                purchaseSchedule.clear();
                actualDate = startDate;
            } else {
                purchaseSchedule.add(PurchaseSchedule.builder()
                                .purchaseDate(actualDate)
                                .quantityToBuy(quantityToBuy)
                        .build());
            }

            actualDate = actualDate.plusDays(1);
        }

        return purchaseSchedule;
    }

    private Integer getQuantityToBuyInfo(InventoryParameters inventoryParameters, LocalDate actualDate, Integer packageFormat, Integer currentStock) {
        var actualDayOfWeek = actualDate.getDayOfWeek().toString();

        if (actualDayOfWeek.equals(inventoryParameters.getPurchaseDay())) {
            var weeklyConsumption = (5 * inventoryParameters.getWorkingDaysConsumption()) + (2 * inventoryParameters.getWeekendConsumption()) - currentStock;
            var roundedUpWeeklyConsumption = ((weeklyConsumption + packageFormat - 1) / packageFormat) * packageFormat;

            return roundedUpWeeklyConsumption;
        }
        return 0;
    }



    private Integer getDailyConsumption(InventoryParameters inventoryParameters, LocalDate actualDate) {
        if (actualDate.getDayOfWeek().getValue() < 5) {
            return inventoryParameters.getWorkingDaysConsumption();
        } else {
            return inventoryParameters.getWeekendConsumption();
        }
    }

    private Integer getBufferStock(InventoryParameters inventoryParameters) {
        var maxConsumption = Math.max(inventoryParameters.getWorkingDaysConsumption(), inventoryParameters.getWeekendConsumption());
        var maxDelay = 7 + inventoryParameters.getDeliveryDelay();
        var averageDailyConsumption = (5 * inventoryParameters.getWorkingDaysConsumption() + 2 * inventoryParameters.getWeekendConsumption()) / 7;
        var averageLeadTime = 7 + inventoryParameters.getDeliveryDelay();
        return (maxConsumption * maxDelay) - (averageDailyConsumption * averageLeadTime);
    }
}
