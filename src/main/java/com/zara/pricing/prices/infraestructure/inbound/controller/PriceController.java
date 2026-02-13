package com.zara.pricing.prices.infraestructure.inbound.controller;

import org.springframework.web.bind.annotation.*;

import com.zara.pricing.prices.infraestructure.inbound.dto.PriceRequestDTO;
import com.zara.pricing.prices.infraestructure.inbound.dto.PriceResponseDTO;
import com.zara.pricing.prices.application.usecase.GetPriceUseCase;
import com.zara.pricing.prices.domain.model.Price;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping("")
    public PriceResponseDTO getPrice(@RequestBody PriceRequestDTO request) {
        Price price = getPriceUseCase.execute(
                request.getProductId(),
                request.getBrandId(),
                request.getApplicationDate()
        );

        return new PriceResponseDTO(
                price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice()
        );
    }
}
