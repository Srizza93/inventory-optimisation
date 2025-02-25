package com.sr.inventory.backend.rest;

import com.sr.inventory.backend.model.InventoryParameters;
import com.sr.inventory.backend.repository.InventoryParametersRepository;
import org.junit.jupiter.api.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Cart Controller Test")
public class OptimisationControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private InventoryParametersRepository inventoryParametersRepository;

    InventoryParameters inventoryParameters;

    @BeforeEach
    void setup() {
        inventoryParameters = inventoryParametersRepository.save(InventoryParameters.builder()
                        .deliveryDelay(2)
                        .packageFormat(2)
                        .workingDaysConsumption(3)
                        .weekendConsumption(4)
                        .purchaseDay("SUNDAY")
                        .currentStock(6)
                .build());
    }

    @AfterEach
    void tearDown() {
        inventoryParametersRepository.deleteAll();
    }

    @Test
    @Tag("IntegrationTest")
    @DisplayName("Should calculate inventory optimisation")
    void it_should_calculate_inventory_optimisation() throws Exception {
        // When & Then
        mvc.perform(get("/inventory")
                        .param("parametersId", inventoryParameters.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryParameters").exists())
                .andExpect(jsonPath("$.purchaseSchedule").exists())
                .andExpect(jsonPath("$.purchaseSchedule[0].currentStock").value(2))
                .andExpect(jsonPath("$.purchaseSchedule[0].purchaseDate").exists())
                .andExpect(jsonPath("$.purchaseSchedule[0].orderAmount").value(30))
                .andExpect(jsonPath("$.purchaseSchedule[1].currentStock").value(0))
                .andExpect(jsonPath("$.purchaseSchedule[1].purchaseDate").exists())
                .andExpect(jsonPath("$.purchaseSchedule[1].orderAmount").value(0))
                .andExpect(jsonPath("$.purchaseSchedule[2].currentStock").value(30))
                .andExpect(jsonPath("$.purchaseSchedule[2].purchaseDate").exists())
                .andExpect(jsonPath("$.purchaseSchedule[2].orderAmount").value(0));
    }

    @Test
    @Tag("IntegrationTest")
    @DisplayName("Should update parameters and calculate inventory optimisation")
    void it_should_update_parameters_and_calculate_inventory_optimisation() throws Exception {
        // Given
        var updatedInventoryParameters = InventoryParameters.builder()
                .id(inventoryParameters.getId())
                .deliveryDelay(3)
                .packageFormat(2)
                .workingDaysConsumption(4)
                .weekendConsumption(5)
                .purchaseDay("SUNDAY")
                .currentStock(6)
                .build();

        var bodyContent = objectMapper.writeValueAsString(updatedInventoryParameters);

        // When & Then
        mvc.perform(put("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inventoryParameters").exists())
                .andExpect(jsonPath("$.purchaseSchedule").exists())
                .andExpect(jsonPath("$.purchaseSchedule[0].currentStock").value(1))
                .andExpect(jsonPath("$.purchaseSchedule[0].purchaseDate").exists())
                .andExpect(jsonPath("$.purchaseSchedule[0].orderAmount").value(45))
                .andExpect(jsonPath("$.purchaseSchedule[1].currentStock").value(0))
                .andExpect(jsonPath("$.purchaseSchedule[1].purchaseDate").exists())
                .andExpect(jsonPath("$.purchaseSchedule[1].orderAmount").value(0))
                .andExpect(jsonPath("$.purchaseSchedule[2].currentStock").value(0))
                .andExpect(jsonPath("$.purchaseSchedule[2].purchaseDate").exists())
                .andExpect(jsonPath("$.purchaseSchedule[2].orderAmount").value(0));
    }

    // test edge cases
    @Test
    @Tag("IntegrationTest")
    @DisplayName("Should return 404 when inventory parameters not found")
    void it_should_return_404_when_inventory_parameters_not_found() throws Exception {
        // When & Then
        mvc.perform(get("/inventory")
                        .param("parametersId", "invalid-id"))
                .andExpect(status().isNotFound());
    }
}
