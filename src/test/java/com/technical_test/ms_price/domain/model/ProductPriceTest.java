package com.technical_test.ms_price.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class ProductPriceTest {

    @Test
    void gettersAndSettersShouldReturnCorrectValues() {

        LocalDateTime startDate = LocalDateTime.parse("2020-06-14T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-12-31T23:59:59");

        BigDecimal priceBigDecimal = new BigDecimal("35.50");

        ProductPrice productPrice1 = new ProductPrice();
        productPrice1.setId(1);
        productPrice1.setProductId(35455);
        productPrice1.setBrandId(1);
        productPrice1.setPriceList(1);
        productPrice1.setPrice(priceBigDecimal);
        productPrice1.setStartDate(startDate);
        productPrice1.setEndDate(endDate);
        productPrice1.setPriority(0);
        productPrice1.setCurrency("EUR");

        Assertions.assertEquals(1, productPrice1.getId());
        Assertions.assertEquals(1, productPrice1.getBrandId());
        Assertions.assertEquals(startDate, productPrice1.getStartDate());
        Assertions.assertEquals(endDate, productPrice1.getEndDate());
        Assertions.assertEquals(1, productPrice1.getPriceList());
        Assertions.assertEquals(35455, productPrice1.getProductId());
        Assertions.assertEquals(0, productPrice1.getPriority());
        Assertions.assertEquals(priceBigDecimal, productPrice1.getPrice());
        Assertions.assertEquals("EUR", productPrice1.getCurrency());
    }

}
