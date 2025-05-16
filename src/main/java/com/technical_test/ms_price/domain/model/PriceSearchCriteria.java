package com.technical_test.ms_price.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PriceSearchCriteria {
    private Integer productId;
    private Integer brandId;
    private LocalDateTime dateFrom;
}
