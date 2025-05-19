package com.technical_test.ms_price.infrastructure.controller;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.application.port.inbound.GetProductPriceUseCase;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebFluxTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private GetProductPriceUseCase getProductPriceUseCase;

    @Test
    void getPriceListByParameters_ValidInput_ReturnsOk() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        LocalDateTime dateTime = LocalDateTime.parse("2020-06-14-10.00.00",formatter);

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455,1, dateTime);

        ProductPriceDTO expectedResponse = new ProductPriceDTO(
                35455,
                1,
                1,
                new BigDecimal("25.50"),
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"));

        Mockito.when(getProductPriceUseCase.getProductPriceByCriteria(Mockito.any(ProductPriceQueryDTO.class)))
                .thenReturn(Mono.just(expectedResponse));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("productId", queryDTO.productId())
                        .queryParam("brandId", queryDTO.brandId())
                        .queryParam("dateFrom", queryDTO.dateFrom())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ProductPriceDTO.class)
                .isEqualTo(expectedResponse);

        Mockito.verify(getProductPriceUseCase, Mockito.times(1))
                .getProductPriceByCriteria(Mockito.any(ProductPriceQueryDTO.class));
    }

}
