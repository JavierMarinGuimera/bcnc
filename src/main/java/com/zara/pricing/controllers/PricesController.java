package com.zara.pricing.controllers;

import org.springframework.web.bind.annotation.*;

import com.zara.pricing.dto.PricesRequestDTO;
import com.zara.pricing.dto.PricesResponseDTO;
import com.zara.pricing.services.PricesService;


@RestController
@RequestMapping("/api")
public class PricesController {

    private final PricesService PricesService;

    public PricesController(PricesService PricesService) {
        this.PricesService = PricesService;
    }    

    @PostMapping("/prices")
    public PricesResponseDTO process(@RequestBody PricesRequestDTO request) {
        return PricesService.process(request);
    }
}