package com.technical_test.ms_price.infrastructure.repository;

import com.technical_test.ms_price.domain.exception.PriceNotFoundException;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.model.ProductPrice;
import com.technical_test.ms_price.domain.port.outbound.PriceRepository;
import com.technical_test.ms_price.infrastructure.common.Constants;
import com.technical_test.ms_price.infrastructure.exception.ResourceNotFoundException;
import com.technical_test.ms_price.infrastructure.repository.mapper.ProductPriceDataMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PriceR2dbcAdapter implements PriceRepository {

    private final ReactivePriceRepository reactivePriceRepository;
    private final ProductPriceDataMapper productPriceDataMapper;

    @Override
    public Mono<ProductPrice> findApplicablePricesByPriceSearchCriteria(PriceSearchCriteria criteria) {
        return reactivePriceRepository.findApplicablePricesByProductIdAndBrandIdAndDate(criteria.getProductId(),criteria.getBrandId(),criteria.getDateFrom())
                .switchIfEmpty(Mono.error(new ResourceNotFoundException(Constants.MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION)))
                .map(productPriceDataMapper::toProductPrice)
                .onErrorMap(throwable -> new PriceNotFoundException(throwable.getMessage(), throwable));
    }
}
