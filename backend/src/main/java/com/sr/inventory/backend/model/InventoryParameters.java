package com.sr.inventory.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inventory_parameters")
public class InventoryParameters {

    @Id
    @UuidGenerator
    @Column(name="id", updatable = false, nullable = false)
    private String id;

    @Column(name="delivery_delay")
    private Integer deliveryDelay;

    @Column(name="package_format")
    private Integer packageFormat;

    @Column(name="working_days_consumption")
    private Integer workingDaysConsumption;

    @Column(name="weekend_consumption")
    private Integer weekendConsumption;

    @Column(name="purchase_day")
    private String purchaseDay;

    @Column(name="current_stock")
    private Integer currentStock;
}
