package com.sr.inventory.backend.controller;

import com.sr.inventory.backend.services.OptimisationCalculation;
import com.sr.inventory.backend.controller.dto.InventoryDto;
import com.sr.inventory.backend.controller.exception.NotFoundException;
import com.sr.inventory.backend.model.InventoryParameters;
import com.sr.inventory.backend.repository.InventoryParametersRepository;
import jakarta.validation.Valid;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class OptimisationController {

    private static final Logger log = LoggerFactory.getLogger(OptimisationController.class);

    private final InventoryParametersRepository inventoryParametersRepository;
    private final OptimisationCalculation optimisationCalculation;

    @GetMapping
    public InventoryDto getInventoryOptimisation(@RequestParam final String parametersId) throws NotFoundException {
        log.info("Optimising inventory");

        var inventoryParameters = inventoryParametersRepository.findById(parametersId)
                .orElseThrow(() -> new NotFoundException("Inventory parameters not found"));

        return InventoryDto.builder()
                .purchaseSchedule(optimisationCalculation.calculateOptimisation(inventoryParameters))
                .inventoryParameters(inventoryParameters)
                .build();
    }

    @PutMapping
    public InventoryDto updateInventoryParameters(@Valid @RequestBody InventoryParameters inventoryParametersRequest) throws NotFoundException {
        log.info("Updating inventory parameters");

        var inventoryParameters = inventoryParametersRepository.findById(inventoryParametersRequest.getId())
                .orElseThrow(() -> new NotFoundException("Inventory parameters not found"));

        optimisationCalculation.validateParameters(inventoryParametersRequest);

        var updatedInventoryParameters = inventoryParametersRepository.save(inventoryParametersRequest);

        return InventoryDto.builder()
                .purchaseSchedule(optimisationCalculation.calculateOptimisation(inventoryParameters))
                .inventoryParameters(updatedInventoryParameters)
                .build();
    }
}
