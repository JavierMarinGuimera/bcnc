package com.zara.pricing.prices.infraestructure.outbound.persistence;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {

    Optional<PriceJpaEntity> findFirstByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByPriorityDesc(
        Long productId,
        Long brandId,
        LocalDateTime applicationDate,
        LocalDateTime applicationDate2
    );
}
