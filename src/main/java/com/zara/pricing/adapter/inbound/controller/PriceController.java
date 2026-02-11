package com.zara.pricing.adapter.inbound.controller;

import org.springframework.web.bind.annotation.*;

import com.zara.pricing.adapter.inbound.dto.PriceRequestDTO;
import com.zara.pricing.adapter.inbound.dto.PriceResponseDTO;
import com.zara.pricing.application.usecase.GetPriceUseCase;
import com.zara.pricing.domain.model.Price;

@RestController
@RequestMapping("/api")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase) {
        this.getPriceUseCase = getPriceUseCase;
    }

    @GetMapping("/prices")
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
