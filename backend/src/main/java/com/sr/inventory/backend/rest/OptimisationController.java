package com.sr.inventory.backend.rest;

import com.sr.inventory.backend.business.service.OptimisationCalculation;
import com.sr.inventory.backend.dto.InventoryDto;
import com.sr.inventory.backend.repository.InventoryParametersRepository;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class OptimisationController {

    private static final Logger log = LoggerFactory.getLogger(OptimisationController.class);

    @Value("${inventory.optimisation.id}")
    private String parametersId;

    private final InventoryParametersRepository inventoryParametersRepository;
    private final OptimisationCalculation optimisationCalculation;

    @GetMapping
    public InventoryDto getInventoryOptimisation() {
        log.info("Optimising inventory");

        var inventoryParameters = inventoryParametersRepository.getReferenceById(parametersId);

        return InventoryDto.builder()
                .quantityRecommendationToBuy(optimisationCalculation.calculateOptimisation(inventoryParameters))
                .build();
    }
}
