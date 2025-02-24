package com.sr.inventory.backend.rest;

import com.sr.inventory.backend.business.service.OptimisationCalculation;
import com.sr.inventory.backend.dto.Inventory;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class OptimisationController {

    private static final Logger log = LoggerFactory.getLogger(OptimisationController.class);

    @Value("${inventory.optimisation.deliveryDelayDays}")
    private int deliveryDelay;

    @Value("${inventory.optimisation.packageSize}")
    private int packageFormat;

    private final OptimisationCalculation optimisationCalculation;

    @GetMapping
    public Inventory getInventoryOptimisation() {
        log.info("Optimising inventory");

        return Inventory.builder()
                .quantityRecommendationToBuy(optimisationCalculation.calculateOptimisation(deliveryDelay, packageFormat))
                .build();
    }
}
