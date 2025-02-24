package com.sr.inventory.backend.business.service;

import com.sr.inventory.backend.model.InventoryParameters;
import org.springframework.stereotype.Service;

@Service
public class OptimisationCalculation {

    public Integer calculateOptimisation(InventoryParameters inventoryParameters) {
        return inventoryParameters.getDeliveryDelay() * inventoryParameters.getPackageFormat();
    }
}
