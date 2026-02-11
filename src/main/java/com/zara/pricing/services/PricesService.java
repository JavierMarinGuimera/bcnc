package com.zara.pricing.services;

import com.zara.pricing.dto.PricesRequestDTO;
import com.zara.pricing.dto.PricesResponseDTO;

public interface PricesService {

    PricesResponseDTO process(PricesRequestDTO request);
}