package com.technical_test.ms_price.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductPriceDTO(
        Integer productId,
        Integer brandId,
        Integer priceList,
        BigDecimal price,
        LocalDateTime startDate,
        LocalDateTime endDate) {

}
