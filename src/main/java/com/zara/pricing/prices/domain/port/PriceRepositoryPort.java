package com.zara.pricing.prices.domain.port;

import java.time.LocalDateTime;
import java.util.List;

import com.zara.pricing.prices.domain.model.Price;

public interface PriceRepositoryPort {
    List<Price> findApplicablePrices(Long productId, Long brandId, LocalDateTime date);
}
