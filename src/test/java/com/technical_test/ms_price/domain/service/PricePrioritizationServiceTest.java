package com.technical_test.ms_price.domain.service;

import com.technical_test.ms_price.domain.model.PriceEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PricePrioritizationServiceTest {

    private final PricePrioritizationService pricePrioritizationService = new PricePrioritizationService();

    @Test
    public void findHighestPriorityPrice_success() {

        List<PriceEntity> priceList = new ArrayList<>();

        PriceEntity price1 = PriceEntity.builder()
                .brandId(1)
                .startDate(buildToLocationDateTime("2020-06-14-00.00.00"))
                .endDate(buildToLocationDateTime("2020-12-31-23.59.59"))
                .priceList(1)
                .productId(35455)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        PriceEntity price2 = PriceEntity.builder()
                .brandId(1)
                .startDate(buildToLocationDateTime("2020-06-14-15.00.00"))
                .endDate(buildToLocationDateTime("2020-06-14-18.30.00"))
                .priceList(2)
                .productId(35455)
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        priceList.add(price1);
        priceList.add(price2);

        PriceEntity priorityPrice = pricePrioritizationService.findHighestPriorityPrice(priceList);

        Assertions.assertEquals(price2.getBrandId(),priorityPrice.getBrandId());
        Assertions.assertEquals(price2.getStartDate(),priorityPrice.getStartDate());
        Assertions.assertEquals(price2.getEndDate(),priorityPrice.getEndDate());
        Assertions.assertEquals(price2.getPriceList(),priorityPrice.getPriceList());
        Assertions.assertEquals(price2.getProductId(),priorityPrice.getProductId());
        Assertions.assertEquals(price2.getPriority(),priorityPrice.getPriority());
        Assertions.assertEquals(price2.getPrice(),priorityPrice.getPrice());
        Assertions.assertEquals(price2.getCurrency(),priorityPrice.getCurrency());

    }

    @Test
    public void findHighestPriorityPrice_with_empty_list() {

        PriceEntity priorityPrice = pricePrioritizationService.findHighestPriorityPrice(Collections.emptyList());

        Assertions.assertNull(priorityPrice);

    }

    @Test
    public void findHighestPriorityPrice_with_null_list() {

        PriceEntity priorityPrice = pricePrioritizationService.findHighestPriorityPrice(null);

        Assertions.assertNull(priorityPrice);

    }

    @Test
    public void findHighestPriorityPrice_with_a_single_object() {

        List<PriceEntity> priceList = new ArrayList<>();

        PriceEntity price2 = PriceEntity.builder()
                .brandId(1)
                .startDate(buildToLocationDateTime("2020-06-14-15.00.00"))
                .endDate(buildToLocationDateTime("2020-06-14-18.30.00"))
                .priceList(2)
                .productId(35455)
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        priceList.add(price2);

        PriceEntity priorityPrice = pricePrioritizationService.findHighestPriorityPrice(priceList);

        Assertions.assertEquals(price2.getBrandId(),priorityPrice.getBrandId());
        Assertions.assertEquals(price2.getStartDate(),priorityPrice.getStartDate());
        Assertions.assertEquals(price2.getEndDate(),priorityPrice.getEndDate());
        Assertions.assertEquals(price2.getPriceList(),priorityPrice.getPriceList());
        Assertions.assertEquals(price2.getProductId(),priorityPrice.getProductId());
        Assertions.assertEquals(price2.getPriority(),priorityPrice.getPriority());
        Assertions.assertEquals(price2.getPrice(),priorityPrice.getPrice());
        Assertions.assertEquals(price2.getCurrency(),priorityPrice.getCurrency());

    }

    private LocalDateTime buildToLocationDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return LocalDateTime.parse(dateTime, formatter);
    }

}
