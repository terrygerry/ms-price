package com.technical_test.ms_price.infrastructure.repository;

import com.technical_test.ms_price.domain.model.Price;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.port.outbound.PriceRepository;
import com.technical_test.ms_price.infrastructure.common.Constants;
import com.technical_test.ms_price.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class PriceR2dbcAdapter implements PriceRepository {
    private final ReactivePriceRepository reactivePriceRepository;

    @Override
    public Flux<Price> findApplicablePricesByPriceSearchCriteria(PriceSearchCriteria criteria) {
        return reactivePriceRepository.findApplicablePricesByProductIdAndBrandIdAndDate(criteria.getProductId(),criteria.getBrandId(),criteria.getDateFrom())
                .switchIfEmpty(Flux.error(new ResourceNotFoundException(Constants.MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION)));
    }
}
