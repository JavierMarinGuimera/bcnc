package com.zara.pricing.prices.application.usecase;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.zara.pricing.prices.domain.exception.PriceNotFoundException;
import com.zara.pricing.prices.domain.model.Price;
import com.zara.pricing.prices.domain.port.PriceRepositoryPort;

@Component
public class GetPriceUseCase {
    
    private final PriceRepositoryPort priceRepository;

    public GetPriceUseCase(PriceRepositoryPort priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Price execute(Long productId, Long brandId, LocalDateTime applicationDate) {
        Optional<Price> price = priceRepository.findApplicablePrice(productId, brandId, applicationDate);

        return price.orElseThrow(() -> new PriceNotFoundException("No price found for the given criteria"));
    }
}
