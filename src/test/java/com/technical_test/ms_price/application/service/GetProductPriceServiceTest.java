package com.technical_test.ms_price.application.service;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.application.mapper.ProductPriceMapper;
import com.technical_test.ms_price.application.mapper.ProductPriceMapperImpl;
import com.technical_test.ms_price.application.mapper.ProductPriceQueryDtoMapperImpl;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.model.ProductPrice;
import com.technical_test.ms_price.domain.port.outbound.PriceRepository;
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
class GetProductPriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    private final ProductPriceQueryDtoMapperImpl priceQueryDtoMapper = new ProductPriceQueryDtoMapperImpl();
    private final ProductPriceMapper productPriceMapper = new ProductPriceMapperImpl();

    private GetProductPriceService getProductPriceService;

    @BeforeEach
    void setUp() {
        getProductPriceService = new GetProductPriceService(priceRepository,priceQueryDtoMapper,productPriceMapper);
    }

    @Test
    void getProductPriceByCriteria_validInput_returnsProductPriceDTO() {

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455, 1, LocalDateTime.parse("2020-06-15T10:00:00"));

        ProductPrice productPrice2 = new ProductPrice();
        productPrice2.setId(2);
        productPrice2.setProductId(35455);
        productPrice2.setBrandId(1);
        productPrice2.setPriceList(3);
        productPrice2.setPrice(new BigDecimal("25.00"));
        productPrice2.setStartDate(LocalDateTime.parse("2020-06-15T00:00:00"));
        productPrice2.setEndDate(LocalDateTime.parse("2020-06-15T11:00:00"));
        productPrice2.setPriority(1);
        productPrice2.setCurrency("EUR");

        Mockito.when(priceRepository.findApplicablePricesByPriceSearchCriteria(Mockito.any(PriceSearchCriteria.class)))
                .thenReturn(Mono.just(productPrice2));

        Mono<ProductPriceDTO> result = getProductPriceService.getProductPriceByCriteria(queryDTO);

        StepVerifier.create(result)
                .expectNext(new ProductPriceDTO(35455, 1, 3, new BigDecimal("25.00"), productPrice2.getStartDate(), productPrice2.getEndDate()))
                .verifyComplete();
    }

}
