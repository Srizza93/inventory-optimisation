package com.sr.inventory.backend.dto;

import com.sr.inventory.backend.model.InventoryParameters;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryDto {

    private InventoryParameters inventoryParameters;
    private List<PurchaseSchedule> purchaseSchedule;
}
