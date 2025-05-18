package com.technical_test.ms_price.infrastructure.repository;

import com.technical_test.ms_price.domain.model.Price;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.port.outbound.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class PriceR2dbcAdapter implements PriceRepository {
    private final ReactivePriceRepository reactivePriceRepository;

    @Override
    public Flux<Price> findApplicablePricesByPriceSearchCriteria(PriceSearchCriteria criteria) {
        return reactivePriceRepository.findApplicablePricesByProductIdAndBrandIdAndDate(criteria.getProductId(),criteria.getBrandId(),criteria.getDateFrom());
    }
}
