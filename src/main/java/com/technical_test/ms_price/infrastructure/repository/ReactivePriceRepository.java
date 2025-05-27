package com.technical_test.ms_price.infrastructure.repository;

import com.technical_test.ms_price.infrastructure.repository.data.ProductPriceData;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface ReactivePriceRepository extends R2dbcRepository<ProductPriceData, Integer> {

    @Query("SELECT * FROM prices " +
            "WHERE product_id = :productId " +
            "AND brand_id = :brandId " +
            "AND start_date <= :dateFrom " +
            "AND end_date >= :dateFrom " +
            "ORDER BY priority DESC " +
            "LIMIT 1")
    Mono<ProductPriceData> findApplicablePricesByProductIdAndBrandIdAndDate(Integer productId, Integer brandId, LocalDateTime dateFrom);

}
