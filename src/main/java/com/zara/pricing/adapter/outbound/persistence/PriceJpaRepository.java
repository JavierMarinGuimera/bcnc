package com.zara.pricing.adapter.outbound.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long> {

    @Query("""
        SELECT p FROM PriceJpaEntity p
        WHERE p.productId = :productId
        AND p.brandId = :brandId
        AND :date BETWEEN p.startDate AND p.endDate
        ORDER BY p.priority DESC
        LIMIT 1
    """)
    List<PriceJpaEntity> findApplicablePrices(
        Long productId,
        Long brandId,
        LocalDateTime date
    );
}
