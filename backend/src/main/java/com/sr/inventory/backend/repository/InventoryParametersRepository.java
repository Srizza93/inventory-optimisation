package com.sr.inventory.backend.repository;

import com.sr.inventory.backend.model.InventoryParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryParametersRepository extends JpaRepository<InventoryParameters, String> {
}
