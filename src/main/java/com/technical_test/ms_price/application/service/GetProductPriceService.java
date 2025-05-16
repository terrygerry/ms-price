package com.technical_test.ms_price.application.service;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.application.port.inbound.GetProductPriceUseCase;
import com.technical_test.ms_price.domain.model.Price;
import com.technical_test.ms_price.domain.model.PriceSearchCriteria;
import com.technical_test.ms_price.domain.ports.outbound.PriceRepository;
import com.technical_test.ms_price.domain.service.PricePrioritizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class GetProductPriceService implements GetProductPriceUseCase {

    private final PriceRepository priceRepository;
    private final PricePrioritizationService pricePrioritizationService;

    @Override
    public Mono<ProductPriceDTO> getProductPriceByCriteria(ProductPriceQueryDTO productPriceQueryDTO) {
        return priceRepository.findByCriteria(buildToPriceSearchCriteria(productPriceQueryDTO))
                .collectList()
                .flatMap(prices -> Mono.just(pricePrioritizationService.findHighestPriorityPrice(prices)))
                .map(buildToProductPriceDTO());
    }

    private Function<Price, ProductPriceDTO> buildToProductPriceDTO() {
        return price -> new ProductPriceDTO(price.getProductId(),
                price.getBrandId(),
                price.getPriceList(),
                price.getPrice(),
                price.getStartDate(),
                price.getEndDate());
    }

    private PriceSearchCriteria buildToPriceSearchCriteria(ProductPriceQueryDTO productPriceQueryDTO) {
        return PriceSearchCriteria.builder()
                .dateFrom(productPriceQueryDTO.dateFrom())
                .brandId(productPriceQueryDTO.brandId())
                .productId(productPriceQueryDTO.productId())
                .build();
    }
}
