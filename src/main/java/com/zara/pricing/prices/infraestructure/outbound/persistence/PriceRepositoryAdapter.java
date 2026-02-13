package com.zara.pricing.prices.infraestructure.outbound.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.zara.pricing.prices.domain.model.Price;
import com.zara.pricing.prices.domain.port.PriceRepositoryPort;

@Component
public class PriceRepositoryAdapter implements PriceRepositoryPort {

    private final PriceJpaRepository priceJpaRepository;

    public PriceRepositoryAdapter(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    @Override
    public List<Price> findApplicablePrices(Long productId, Long brandId, LocalDateTime date) {
        List<PriceJpaEntity> entities = priceJpaRepository.findApplicablePrices(productId, brandId, date);
        return entities.stream()
                .map(this::toDomainModel)
                .toList();
    }

    private Price toDomainModel(PriceJpaEntity entity) {
        return new Price(
                entity.getId(),
                entity.getProductId(),
                entity.getBrandId(),
                entity.getPriceList(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getPriority(),
                entity.getPrice(),
                entity.getCurr()
        );
    }
}
