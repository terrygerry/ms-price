package com.technical_test.ms_price.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PriceTest {

    @Test
    public void canCreatePriceIntanceUsingBuilder(){
        String startDateTimeSt = "2020-06-14-00.00.00";
        String endDateTimeSt = "2020-12-31-23.59.59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        LocalDateTime startDate = LocalDateTime.parse(startDateTimeSt, formatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateTimeSt, formatter);

        BigDecimal priceBigDecimal = new BigDecimal("35.50");

        PriceEntity price = PriceEntity.builder()
                .brandId(1)
                .startDate(startDate)
                .endDate(endDate)
                .priceList(1)
                .productId(35455)
                .priority(0)
                .price(priceBigDecimal)
                .currency("EUR")
                .build();

        Assertions.assertEquals(1,price.getBrandId());
        Assertions.assertEquals("2020-06-14T00:00",price.getStartDate().toString());
        Assertions.assertEquals("2020-12-31T23:59:59",price.getEndDate().toString());
        Assertions.assertEquals(1,price.getPriceList());
        Assertions.assertEquals(35455,price.getProductId());
        Assertions.assertEquals(0,price.getPriority());
        Assertions.assertEquals(new BigDecimal("35.50"),price.getPrice());
        Assertions.assertEquals("EUR",price.getCurrency());

    }

    @Test
    void gettersShouldReturnCorrectValues() {
        String startDateTimeSt = "2020-06-14-00.00.00";
        String endDateTimeSt = "2020-12-31-23.59.59";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        LocalDateTime startDate = LocalDateTime.parse(startDateTimeSt, formatter);
        LocalDateTime endDate = LocalDateTime.parse(endDateTimeSt, formatter);

        BigDecimal priceBigDecimal = new BigDecimal("35.50");

        PriceEntity price = new PriceEntity(1,1,startDate,endDate,1,35455,0,priceBigDecimal,"EUR");

        Assertions.assertEquals(1, price.getBrandId());
        Assertions.assertEquals(startDate, price.getStartDate());
        Assertions.assertEquals(endDate, price.getEndDate());
        Assertions.assertEquals(1, price.getPriceList());
        Assertions.assertEquals(35455, price.getProductId());
        Assertions.assertEquals(0, price.getPriority());
        Assertions.assertEquals(new BigDecimal("35.50"), price.getPrice());
        Assertions.assertEquals("EUR", price.getCurrency());
    }

    @Test
    void settersShouldModifyStateCorrectly() {
        PriceEntity price = new PriceEntity();

        String startDateTimeSt = "2020-06-14-00.00.00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        LocalDateTime startDate = LocalDateTime.parse(startDateTimeSt, formatter);

        BigDecimal priceBigDecimal = new BigDecimal("35.50");

        price.setBrandId(1);
        price.setStartDate(startDate);
        price.setPrice(priceBigDecimal);

        Assertions.assertEquals(1, price.getBrandId());
        Assertions.assertEquals(startDate, price.getStartDate());
        Assertions.assertEquals(priceBigDecimal, price.getPrice());
    }

}
