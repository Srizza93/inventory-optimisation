package com.sr.inventory.backend.rest;

import com.sr.inventory.backend.business.service.OptimisationCalculation;
import com.sr.inventory.backend.dto.InventoryDto;
import com.sr.inventory.backend.model.InventoryParameters;
import com.sr.inventory.backend.repository.InventoryParametersRepository;
import jakarta.validation.Valid;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

        var inventoryParameters = inventoryParametersRepository.findById(parametersId)
                .orElseThrow(() -> new RuntimeException("Inventory parameters not found"));

        return InventoryDto.builder()
                .purchaseSchedule(optimisationCalculation.calculateOptimisation(inventoryParameters))
                .inventoryParameters(inventoryParameters)
                .build();
    }

    @PutMapping
    public InventoryDto updateInventoryParameters(@Valid @RequestBody InventoryParameters inventoryParametersRequest) {
        log.info("Updating inventory parameters");

        var inventoryParameters = inventoryParametersRepository.findById(parametersId)
                .orElseThrow(() -> new RuntimeException("Inventory parameters not found"));

        var updatedInventoryParameters = inventoryParametersRepository.save(inventoryParametersRequest);

        return InventoryDto.builder()
                .purchaseSchedule(optimisationCalculation.calculateOptimisation(inventoryParameters))
                .inventoryParameters(updatedInventoryParameters)
                .build();
    }
}
