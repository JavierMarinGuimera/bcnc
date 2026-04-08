package com.zara.pricing.prices.domain.port;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.zara.pricing.prices.domain.model.Price;

public interface PriceRepositoryPort {
    Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime date);

    List<Price> findAllPrices();
}
