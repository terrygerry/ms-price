package com.technical_test.ms_price.infrastructure.repository;

import com.technical_test.ms_price.domain.model.PriceEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@DataR2dbcTest
@ActiveProfiles("test")
public class ReactivePriceRepositoryTest {

    @Autowired
    private ReactivePriceRepository reactivePriceRepository;

    @Autowired
    private DatabaseClient databaseClient;

    @Test
    public void findApplicablePricesByProductIdAndBrandIdAndDate_returnsCorrectPrices() {

        insertTestData(1,
                1,
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                1,
                35455,
                0,
                new BigDecimal("35.50"),
                "EUR");

        insertTestData(2,
                1,
                LocalDateTime.parse("2020-06-15T00:00:00"),
                LocalDateTime.parse("2020-06-15T11:00:00"),
                3,
                35455,
                1,
                new BigDecimal("30.50"),
                "EUR");

        insertTestData(3,
                1,
                LocalDateTime.parse("2020-06-15T16:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                4,
                35455,
                1,
                new BigDecimal("38.95"),
                "EUR");

        Flux<PriceEntity> prices = reactivePriceRepository.findApplicablePricesByProductIdAndBrandIdAndDate(35455, 1, LocalDateTime.parse("2020-06-15T10:00:00"));

        StepVerifier.create(prices)
                .expectNextMatches(price -> price.getPrice().equals(new BigDecimal("35.50")) || price.getPrice().equals(new BigDecimal("30.50")))
                .expectNextMatches(price -> price.getPrice().equals(new BigDecimal("35.50")) || price.getPrice().equals(new BigDecimal("30.50")))
                .verifyComplete();
    }

    private void insertTestData(Integer id, Integer brandId, LocalDateTime startDate, LocalDateTime endDate, Integer priceList, Integer productId, Integer priority, BigDecimal priceValue, String currency) {
        databaseClient.sql("INSERT INTO prices (id, brand_id, start_date, end_date, price_list, product_id, priority, price, currency) VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9)")
                .bind(0, id)
                .bind(1, brandId)
                .bind(2, startDate)
                .bind(3, endDate)
                .bind(4, priceList)
                .bind(5, productId)
                .bind(6, priority)
                .bind(7, priceValue)
                .bind(8, currency)
                .fetch().rowsUpdated().block();
    }

}
