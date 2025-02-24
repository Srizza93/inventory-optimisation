package com.sr.inventory.backend.business.service;

import com.sr.inventory.backend.dto.PurchaseSchedule;
import com.sr.inventory.backend.model.InventoryParameters;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OptimisationCalculation {

    LocalDate startDate = LocalDate.of(2025, 1, 6); // Sunday, January 6, 2025
    LocalDate endDate = startDate.plusYears(1);

    public List<PurchaseSchedule> calculateOptimisation(InventoryParameters inventoryParameters) {

        var actualDate = startDate;
        List<PurchaseSchedule> purchaseSchedule = new ArrayList<>();
        var currentStock = inventoryParameters.getCurrentStock();

        while (actualDate.isBefore(endDate)) {
            var quantityToBuy = 0;

            // Consumption
            if (actualDate.getDayOfWeek().getValue() < 5) {
                inventoryParameters.setCurrentStock(currentStock - inventoryParameters.getWorkingDaysConsumption());
            } else {
                inventoryParameters.setCurrentStock(currentStock - inventoryParameters.getWeekendConsumption());
            }

            // Purchase
            if (actualDate.getDayOfWeek().toString().equals(inventoryParameters.getPurchaseDay())) {
                quantityToBuy = getWeeklyConsumption(inventoryParameters);
                inventoryParameters.setCurrentStock(currentStock + quantityToBuy);
            }

            // Out of stock check
            if (inventoryParameters.getCurrentStock() < 0) {
                inventoryParameters.setPackageFormat(inventoryParameters.getPackageFormat() + 1);
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

    private Integer getWeeklyConsumption(InventoryParameters inventoryParameters) {
        int workingDaysConsumption = inventoryParameters.getWorkingDaysConsumption();
        int weekendConsumption = inventoryParameters.getWeekendConsumption();
        int packageFormat = inventoryParameters.getPackageFormat();

        int consumption = (5 * workingDaysConsumption) + (2 * weekendConsumption);
        return (consumption + packageFormat - 1) / packageFormat * packageFormat;
    }
}
