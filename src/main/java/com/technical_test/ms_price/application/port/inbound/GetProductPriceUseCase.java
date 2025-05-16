package com.technical_test.ms_price.application.port.inbound;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import reactor.core.publisher.Mono;

public interface GetProductPriceUseCase {

    Mono<ProductPriceDTO> getProductPriceByCriteria(ProductPriceQueryDTO productPriceQueryDTO);

}
