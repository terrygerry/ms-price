package com.technical_test.ms_price.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public record ProductPriceQueryDTO(
        @NotNull(message = "El productId no puede ser nulo")
        @Positive(message = "El productId debe ser un valor positivo")
        Integer productId,
        @NotNull(message = "El brandId no puede ser nulo")
        @Positive(message = "El brandId debe ser un valor positivo")
        Integer brandId,
        @DateTimeFormat(pattern = "yyyy-MM-dd-HH.mm.ss")
        @NotNull(message = "La fecha de aplicación no puede ser nula")
        LocalDateTime dateFrom
) {
}
