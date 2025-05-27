package com.technical_test.ms_price.infrastructure.controller;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.application.port.inbound.GetProductPriceUseCase;
import com.technical_test.ms_price.domain.exception.PriceNotFoundException;
import com.technical_test.ms_price.infrastructure.api.response.ErrorResponse;
import com.technical_test.ms_price.infrastructure.common.Constants;
import com.technical_test.ms_price.infrastructure.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void getPriceListByParameters_InvalidInput_Returns_Error(){

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(0,1, LocalDateTime.parse("2020-06-14T10:00:00"));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("productId", queryDTO.productId())
                        .queryParam("brandId", queryDTO.brandId())
                        .queryParam("dateFrom", queryDTO.dateFrom())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ErrorResponse.class);

    }

    @Test
    void getPriceListByParameters_InvalidInput_dateFrom_Returns_Error(){

        String message = "dateFrom: El dateFrom debe tener este formato yyyy-MM-dd-HH.mm.ss";

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("productId", 35455)
                        .queryParam("brandId", 1)
                        .queryParam("dateFrom", "2020-06-14")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ErrorResponse.class)
                .consumeWith(result -> {
                    ErrorResponse errorResponse = result.getResponseBody();
                    Assertions.assertEquals(message, errorResponse.getDetails());
                });

    }

    @Test
    void getPriceListByParameters_With_Error_In_Search_Returns_Error(){

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455,1, LocalDateTime.parse("2020-06-14T00:00:00"));

        Mockito.when(getProductPriceUseCase.getProductPriceByCriteria(Mockito.any(ProductPriceQueryDTO.class)))
                .thenReturn(Mono.error(new PriceNotFoundException(Constants.MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION, new ResourceNotFoundException(Constants.MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION))));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/prices")
                        .queryParam("productId", queryDTO.productId())
                        .queryParam("brandId", queryDTO.brandId())
                        .queryParam("dateFrom", queryDTO.dateFrom())
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(ErrorResponse.class);
    }

}
