package com.technical_test.ms_price.domain.ports.outbound;

import com.technical_test.ms_price.domain.model.Price;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import reactor.core.publisher.Flux;

public interface PriceRepository {
    Flux<Price> findByCriteria(PriceSearchCriteria priceSearchCriteria);
}
