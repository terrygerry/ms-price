package com.technical_test.ms_price.domain.service;

import com.technical_test.ms_price.domain.model.Price;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PricePrioritizationService {
    public Price findHighestPriorityPrice(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            return null;
        }
        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElse(null);
    }
}
