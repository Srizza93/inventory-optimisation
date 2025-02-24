package com.sr.inventory.backend.business.service;

import org.springframework.stereotype.Service;

@Service
public class OptimisationCalculation {

    public int calculateOptimisation(int deliveryDelay, int packageFormat) {
        return deliveryDelay * packageFormat;
    }
}
