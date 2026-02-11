package com.zara.pricing.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PricesRequestDTO {
    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
}
