package com.technical_test.ms_price.domain.service;

import com.technical_test.ms_price.domain.model.PriceEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PricePrioritizationService {
    public PriceEntity findHighestPriorityPrice(List<PriceEntity> prices) {
        if (prices == null || prices.isEmpty()) {
            return null;
        }
        return prices.stream()
                .max(Comparator.comparingInt(PriceEntity::getPriority))
                .orElse(null);
    }
}
