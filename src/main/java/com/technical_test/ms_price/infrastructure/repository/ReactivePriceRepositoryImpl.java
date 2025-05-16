package com.technical_test.ms_price.infrastructure.repository;

import com.technical_test.ms_price.domain.model.Price;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.ports.outbound.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class ReactivePriceRepositoryImpl implements PriceRepository {
    private final ReactivePriceRepository reactivePriceRepository;

    @Override
    public Flux<Price> findByCriteria(PriceSearchCriteria priceSearchCriteria) {
        return reactivePriceRepository.findAll();
    }
}
