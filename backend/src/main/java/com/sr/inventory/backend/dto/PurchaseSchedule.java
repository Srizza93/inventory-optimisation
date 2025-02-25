package com.sr.inventory.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseSchedule {

    private LocalDate purchaseDate;
    private Integer orderAmount;
    private Integer currentStock;
}
