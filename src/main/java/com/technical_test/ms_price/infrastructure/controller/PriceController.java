package com.technical_test.ms_price.infrastructure.controller;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.application.port.inbound.GetProductPriceUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/prices")
@Slf4j
@RequiredArgsConstructor
public class PriceController {

    private final GetProductPriceUseCase getProductPriceUseCase;

    @GetMapping
    public Mono<ProductPriceDTO> getPriceListByParameters(@Validated ProductPriceQueryDTO productPriceQueryDTO) {
        log.info("Request GET /prices with parameters: {}", productPriceQueryDTO);
        return getProductPriceUseCase.getProductPriceByCriteria(productPriceQueryDTO)
                .doOnError(throwable -> log.error("Error Request GET /prices", throwable));
    }

}
