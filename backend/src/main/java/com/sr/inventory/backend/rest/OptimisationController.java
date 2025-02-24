package com.sr.inventory.backend.rest;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

@RestController
@RequestMapping("/inventory")
@CustomLog
@RequiredArgsConstructor
public class OptimisationController {

    private static final Logger log = LoggerFactory.getLogger(OptimisationController.class);

    @Value("${inventory.optimisation.deliveryDelayDays}")
    private int deliveryDelay;

    @Value("${inventory.optimisation.packageSize}")
    private int packageFormat;

    @GetMapping()
    public Inventory getInventoryOptimisation() {
        log.info("Optimising inventory");
        // Optimisation logic
    }
}
