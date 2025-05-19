package com.technical_test.ms_price.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "Parameters of request to search price")
public record ProductPriceQueryDTO(
        @NotNull(message = "El productId no puede ser nulo")
        @Positive(message = "El productId debe ser un valor positivo")
        @Schema(description = "Unique Identify of product", example = "35455")
        Integer productId,
        @NotNull(message = "El brandId no puede ser nulo")
        @Positive(message = "El brandId debe ser un valor positivo")
        @Schema(description = "Unique Identify of brand", example = "1")
        Integer brandId,
        @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
        @NotNull(message = "La fecha de aplicación no puede ser nula")
        @Schema(description = "Search Data", example = "2025-05-19-11.30.00")
        LocalDateTime dateFrom
) {
}
