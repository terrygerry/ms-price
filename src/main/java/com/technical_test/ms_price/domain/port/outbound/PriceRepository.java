package com.technical_test.ms_price.domain.port.outbound;

import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.model.ProductPrice;
import reactor.core.publisher.Mono;

public interface PriceRepository {
    Mono<ProductPrice> findApplicablePricesByPriceSearchCriteria(PriceSearchCriteria priceSearchCriteria);
}
