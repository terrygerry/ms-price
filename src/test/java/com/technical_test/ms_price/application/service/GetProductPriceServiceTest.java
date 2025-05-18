package com.technical_test.ms_price.application.service;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.domain.model.Price;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.port.outbound.PriceRepository;
import com.technical_test.ms_price.domain.service.PricePrioritizationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class GetProductPriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PricePrioritizationService pricePrioritizationService;

    @InjectMocks
    private GetProductPriceService getProductPriceService;

    @Test
    void getProductPriceByCriteria_validInput_returnsProductPriceDTO() {

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455, 1, LocalDateTime.parse("2020-06-15T10:00:00"));

        Price price1 = Price.builder()
                .id(1)
                .productId(35455)
                .brandId(1)
                .priceList(1)
                .price(new BigDecimal("35.50"))
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priority(0)
                .currency("EUR")
                .build();
        Price price2 = Price.builder()
                .id(2)
                .productId(35455)
                .brandId(1)
                .priceList(3)
                .price(new BigDecimal("25.00"))
                .startDate(LocalDateTime.parse("2020-06-15T00:00:00"))
                .endDate(LocalDateTime.parse("2020-06-15T11:00:00"))
                .priority(1)
                .currency("EUR")
                .build();

        Mockito.when(priceRepository.findApplicablePricesByPriceSearchCriteria(Mockito.any(PriceSearchCriteria.class)))
                .thenReturn(Flux.just(price1, price2));
        Mockito.when(pricePrioritizationService.findHighestPriorityPrice(Arrays.asList(price1, price2))).thenReturn(price1);

        Mono<ProductPriceDTO> result = getProductPriceService.getProductPriceByCriteria(queryDTO);

        StepVerifier.create(result)
                .expectNext(new ProductPriceDTO(35455, 1, 1, new BigDecimal("35.50"), price1.getStartDate(), price1.getEndDate()))
                .verifyComplete();
    }

}
