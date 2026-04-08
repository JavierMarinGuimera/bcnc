package com.zara.pricing.prices.infraestructure.outbound.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime date) {
        return priceJpaRepository
                .findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
                        productId, brandId, date, date)
                .map(this::toDomainModel);
    }

    @Override
    public List<Price> findAllPrices() {
        return priceJpaRepository.findAll().stream()
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
