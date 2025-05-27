package com.technical_test.ms_price.infrastructure.controller;

import com.technical_test.ms_price.MsPriceApplication;
import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MsPriceApplication.class)
@ActiveProfiles("test")
class PriceControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private DatabaseClient databaseClient;

    @BeforeEach
    void setUp() {
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
                LocalDateTime.parse("2020-06-14T15:00:00"),
                LocalDateTime.parse("2020-06-14T18:30:00"),
                2,
                35455,
                1,
                new BigDecimal("25.45"),
                "EUR");

        insertTestData(3,
                1,
                LocalDateTime.parse("2020-06-15T00:00:00"),
                LocalDateTime.parse("2020-06-15T11:00:00"),
                3,
                35455,
                1,
                new BigDecimal("30.50"),
                "EUR");

        insertTestData(4,
                1,
                LocalDateTime.parse("2020-06-15T16:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"),
                4,
                35455,
                1,
                new BigDecimal("38.95"),
                "EUR");

    }

    @AfterEach
    void setFinished() {
        truncateTestData();
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

    private void truncateTestData() {
        databaseClient.sql("TRUNCATE TABLE prices")
                .fetch()
                .rowsUpdated()
                .block();
    }

    @Test
    void getPriceListByParameters_Test_1() {

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455,1, LocalDateTime.parse("2020-06-14T10:00:00"));

        ProductPriceDTO expectedResponse = new ProductPriceDTO(
                35455,
                1,
                1,
                new BigDecimal("35.50"),
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"));

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

    }

    @Test
    void getPriceListByParameters_Test_2() {

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455,1, LocalDateTime.parse("2020-06-14T16:00:00"));

        ProductPriceDTO expectedResponse = new ProductPriceDTO(
                35455,
                1,
                2,
                new BigDecimal("25.45"),
                LocalDateTime.parse("2020-06-14T15:00:00"),
                LocalDateTime.parse("2020-06-14T18:30:00"));

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

    }

    @Test
    void getPriceListByParameters_Test_3() {

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455,1, LocalDateTime.parse("2020-06-14T21:00:00"));

        ProductPriceDTO expectedResponse = new ProductPriceDTO(
                35455,
                1,
                1,
                new BigDecimal("35.50"),
                LocalDateTime.parse("2020-06-14T00:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"));

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

    }

    @Test
    void getPriceListByParameters_Test_4() {

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455,1, LocalDateTime.parse("2020-06-15T10:00:00"));

        ProductPriceDTO expectedResponse = new ProductPriceDTO(
                35455,
                1,
                3,
                new BigDecimal("30.50"),
                LocalDateTime.parse("2020-06-15T00:00:00"),
                LocalDateTime.parse("2020-06-15T11:00:00"));

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

    }

    @Test
    void getPriceListByParameters_Test_5() {

        ProductPriceQueryDTO queryDTO = new ProductPriceQueryDTO(35455,1, LocalDateTime.parse("2020-06-16T21:00:00"));

        ProductPriceDTO expectedResponse = new ProductPriceDTO(
                35455,
                1,
                4,
                new BigDecimal("38.95"),
                LocalDateTime.parse("2020-06-15T16:00:00"),
                LocalDateTime.parse("2020-12-31T23:59:59"));

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

    }

}
