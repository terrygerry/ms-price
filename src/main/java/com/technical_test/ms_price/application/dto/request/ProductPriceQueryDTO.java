package com.technical_test.ms_price.application.dto.request;

import java.time.LocalDateTime;

public record ProductPriceQueryDTO(
        Integer productId,
        Integer brandId,
        LocalDateTime dateFrom
) {
}
