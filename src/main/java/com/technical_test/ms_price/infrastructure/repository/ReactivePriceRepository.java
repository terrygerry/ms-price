package com.technical_test.ms_price.infrastructure.repository;

import com.technical_test.ms_price.domain.model.Price;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface ReactivePriceRepository extends R2dbcRepository<Price, Integer> {
}
