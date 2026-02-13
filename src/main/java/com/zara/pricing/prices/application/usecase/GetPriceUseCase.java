package com.zara.pricing.prices.application.usecase;

import java.time.LocalDateTime;
import java.util.List;

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
        List<Price> prices = priceRepository.findApplicablePrices(productId, brandId, applicationDate);
        
        if (prices.isEmpty()) {
            throw new PriceNotFoundException("No price found for the given criteria");
        }

        return prices.get(0);
    }
}
