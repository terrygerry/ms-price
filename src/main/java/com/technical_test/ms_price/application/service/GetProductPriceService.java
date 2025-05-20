package com.technical_test.ms_price.application.service;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.application.mapper.PriceEntityMapper;
import com.technical_test.ms_price.application.mapper.ProductPriceQueryDtoMapper;
import com.technical_test.ms_price.application.port.inbound.GetProductPriceUseCase;
import com.technical_test.ms_price.domain.model.PriceEntity;
import com.technical_test.ms_price.domain.port.outbound.PriceRepository;
import com.technical_test.ms_price.domain.service.PricePrioritizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetProductPriceService implements GetProductPriceUseCase {

    private final PriceRepository priceRepository;
    private final PricePrioritizationService pricePrioritizationService;
    private final ProductPriceQueryDtoMapper priceQueryDtoMapper;
    private final PriceEntityMapper priceEntityMapper;

    @Override
    public Mono<ProductPriceDTO> getProductPriceByCriteria(ProductPriceQueryDTO productPriceQueryDTO) {
        return priceRepository.findApplicablePricesByPriceSearchCriteria(priceQueryDtoMapper.toPriceSearchCriteria(productPriceQueryDTO))
                .collectList()
                .flatMap(this::getApplicablePrice)
                .map(priceEntityMapper::toProductPriceDTO);
    }

    private Mono<PriceEntity> getApplicablePrice(List<PriceEntity> prices) {
        return Mono.just(pricePrioritizationService.findHighestPriorityPrice(prices));
    }
}
