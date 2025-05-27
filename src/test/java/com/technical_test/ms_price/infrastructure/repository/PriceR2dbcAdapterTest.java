package com.technical_test.ms_price.infrastructure.repository;

import com.technical_test.ms_price.domain.exception.PriceNotFoundException;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.model.ProductPrice;
import com.technical_test.ms_price.infrastructure.repository.data.ProductPriceData;
import com.technical_test.ms_price.infrastructure.repository.mapper.ProductPriceDataMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class PriceR2dbcAdapterTest {

    @Mock
    private ReactivePriceRepository reactivePriceRepository;

    private ProductPriceDataMapperImpl productPriceDataMapper = new ProductPriceDataMapperImpl();

    private PriceR2dbcAdapter priceR2dbcAdapter;

    @BeforeEach
    void setUp(){
        priceR2dbcAdapter = new PriceR2dbcAdapter(reactivePriceRepository,productPriceDataMapper);
    }

    @Test
    void findApplicablePricesByPriceSearchCriteria_validInput_returnsProductPrice() {

        ProductPriceData productPriceData = new ProductPriceData();
        productPriceData.setId(1);
        productPriceData.setProductId(35455);
        productPriceData.setBrandId(1);
        productPriceData.setPriceList(1);
        productPriceData.setPrice(new BigDecimal("35.50"));
        productPriceData.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        productPriceData.setEndDate(LocalDateTime.parse("2020-12-31T23:59:59"));
        productPriceData.setPriority(0);
        productPriceData.setCurrency("EUR");

        PriceSearchCriteria criteria = new PriceSearchCriteria();
        criteria.setProductId(35455);
        criteria.setBrandId(1);
        criteria.setDateFrom(LocalDateTime.parse("2020-06-15T10:00:00"));

        Mockito.when(reactivePriceRepository.findApplicablePricesByProductIdAndBrandIdAndDate(Mockito.anyInt(),Mockito.anyInt(),Mockito.any(java.time.LocalDateTime.class)))
                .thenReturn(Mono.just(productPriceData));

        Mono<ProductPrice> response = priceR2dbcAdapter.findApplicablePricesByPriceSearchCriteria(criteria);

        StepVerifier.create(response)
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    void findApplicablePricesByPriceSearchCriteria_invalidInput_returnsEmpty() {

        PriceSearchCriteria criteria = new PriceSearchCriteria();
        criteria.setProductId(35455);
        criteria.setBrandId(1);
        criteria.setDateFrom(LocalDateTime.parse("2020-06-15T10:00:00"));

        Mockito.when(reactivePriceRepository.findApplicablePricesByProductIdAndBrandIdAndDate(Mockito.anyInt(),Mockito.anyInt(),Mockito.any(java.time.LocalDateTime.class)))
                .thenReturn(Mono.empty());

        Mono<ProductPrice> response = priceR2dbcAdapter.findApplicablePricesByPriceSearchCriteria(criteria);

        StepVerifier.create(response)
                .expectError(PriceNotFoundException.class)
                .verify();

    }

}
