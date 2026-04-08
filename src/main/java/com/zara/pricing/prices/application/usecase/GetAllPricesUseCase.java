package com.zara.pricing.prices.application.usecase;

import java.util.List;

import org.springframework.stereotype.Component;

import com.zara.pricing.prices.domain.model.Price;
import com.zara.pricing.prices.domain.port.PriceRepositoryPort;

@Component
public class GetAllPricesUseCase {

    private final PriceRepositoryPort priceRepository;

    public GetAllPricesUseCase(PriceRepositoryPort priceRepository) {
        this.priceRepository = priceRepository;
    }

    public List<Price> execute() {
        return priceRepository.findAllPrices();
    }
}
