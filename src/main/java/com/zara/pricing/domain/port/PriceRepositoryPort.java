package com.zara.pricing.domain.port;

import java.time.LocalDateTime;
import java.util.List;

import com.zara.pricing.domain.model.Price;

public interface PriceRepositoryPort {
    List<Price> findApplicablePrices(Long productId, Long brandId, LocalDateTime date);

    List<Price> findAllPrices();
}
