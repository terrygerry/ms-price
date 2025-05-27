package com.technical_test.ms_price.application.service;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.application.mapper.ProductPriceMapper;
import com.technical_test.ms_price.application.mapper.ProductPriceQueryDtoMapper;
import com.technical_test.ms_price.application.port.inbound.GetProductPriceUseCase;
import com.technical_test.ms_price.domain.port.outbound.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GetProductPriceService implements GetProductPriceUseCase {

    private final PriceRepository priceRepository;
    private final ProductPriceQueryDtoMapper priceQueryDtoMapper;
    private final ProductPriceMapper productPriceMapper;

    @Override
    public Mono<ProductPriceDTO> getProductPriceByCriteria(ProductPriceQueryDTO productPriceQueryDTO) {
        return priceRepository.findApplicablePricesByPriceSearchCriteria(priceQueryDtoMapper.toPriceSearchCriteria(productPriceQueryDTO))
                .map(productPriceMapper::toProductPriceDTO);
    }

}
