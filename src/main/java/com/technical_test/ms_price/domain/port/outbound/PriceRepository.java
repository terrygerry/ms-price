package com.technical_test.ms_price.domain.port.outbound;

import com.technical_test.ms_price.domain.model.PriceEntity;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import reactor.core.publisher.Flux;

public interface PriceRepository {
    Flux<PriceEntity> findApplicablePricesByPriceSearchCriteria(PriceSearchCriteria priceSearchCriteria);
}
