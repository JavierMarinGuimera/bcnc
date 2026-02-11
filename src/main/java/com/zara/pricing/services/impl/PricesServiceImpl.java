package com.zara.pricing.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.zara.pricing.dto.PricesRequestDTO;
import com.zara.pricing.dto.PricesResponseDTO;
import com.zara.pricing.models.PriceEntity;
import com.zara.pricing.repository.PriceRepository;
import com.zara.pricing.services.PricesService;

@Service
public class PricesServiceImpl implements PricesService {

    private final PriceRepository repository;

    public PricesServiceImpl(PriceRepository repository) {
        this.repository = repository;
    }

    @Override
    public PricesResponseDTO process(PricesRequestDTO request) {
        List<PriceEntity> prices = repository.findApplicablePrices(
                request.getProductId(),
                request.getBrandId(),
                request.getApplicationDate()
        );

        if (prices.isEmpty()) {
            throw new RuntimeException("No price found");
        }

        PriceEntity selected = prices.get(0);

        return new PricesResponseDTO(
                selected.getProductId(),
                selected.getBrandId(),
                selected.getPriceList(),
                selected.getStartDate(),
                selected.getEndDate(),
                selected.getPrice()
        );
    }
}