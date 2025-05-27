package com.technical_test.ms_price.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class PriceSearchCriteriaTest {

    @Test
    void gettersAndSettersShouldReturnCorrectValues() {

        LocalDateTime date = LocalDateTime.parse("2020-06-14T00:00:00");

        PriceSearchCriteria priceSearchCriteria = new PriceSearchCriteria();
        priceSearchCriteria.setProductId(35455);
        priceSearchCriteria.setBrandId(1);
        priceSearchCriteria.setDateFrom(date);

        Assertions.assertEquals(1, priceSearchCriteria.getBrandId());
        Assertions.assertEquals(date, priceSearchCriteria.getDateFrom());
        Assertions.assertEquals(35455, priceSearchCriteria.getProductId());
    }

}
