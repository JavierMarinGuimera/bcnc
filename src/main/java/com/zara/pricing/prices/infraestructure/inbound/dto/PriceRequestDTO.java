package com.zara.pricing.prices.infraestructure.inbound.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceRequestDTO {
    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
}
