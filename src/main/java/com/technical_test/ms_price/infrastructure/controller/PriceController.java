package com.technical_test.ms_price.infrastructure.controller;

import com.technical_test.ms_price.application.dto.request.ProductPriceQueryDTO;
import com.technical_test.ms_price.application.dto.response.ProductPriceDTO;
import com.technical_test.ms_price.application.port.inbound.GetProductPriceUseCase;
import com.technical_test.ms_price.infrastructure.api.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Get product price based on criteria",
            description = "Retrieves the applicable product price based on the provided brand ID, product ID, and application date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful operation",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductPriceDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input parameters",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", description = "No price found for the given criteria",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public Mono<ProductPriceDTO> getPriceListByParameters(@Validated @Parameter(description = "Product price query parameters", required = true,
            schema = @Schema(implementation = ProductPriceQueryDTO.class)) ProductPriceQueryDTO productPriceQueryDTO) {
        log.info("Request GET /prices with parameters: {}", productPriceQueryDTO);
        return getProductPriceUseCase.getProductPriceByCriteria(productPriceQueryDTO)
                .doOnError(throwable -> log.error("Error Request GET /prices", throwable));
    }

}
