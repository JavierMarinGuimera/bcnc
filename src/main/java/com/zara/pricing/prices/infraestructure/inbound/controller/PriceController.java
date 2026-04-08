package com.zara.pricing.prices.infraestructure.inbound.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zara.pricing.prices.application.usecase.GetAllPricesUseCase;
import com.zara.pricing.prices.application.usecase.GetPriceUseCase;
import com.zara.pricing.prices.domain.model.Price;
import com.zara.pricing.prices.infraestructure.inbound.dto.PriceResponseDTO;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final GetAllPricesUseCase getAllPricesUseCase;

    public PriceController(GetPriceUseCase getPriceUseCase, GetAllPricesUseCase getAllPricesUseCase) {
        this.getPriceUseCase = getPriceUseCase;
        this.getAllPricesUseCase = getAllPricesUseCase;
    }

    @GetMapping
    public List<PriceResponseDTO> getAllPrices() {
        return getAllPricesUseCase.execute().stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/get-price")
    public PriceResponseDTO getPrice(
            @RequestParam Long productId,
            @RequestParam Long brandId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate
    ) {
        Price price = getPriceUseCase.execute(productId, brandId, applicationDate);
        return toResponseDto(price);
    }

    private PriceResponseDTO toResponseDto(Price price) {
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
