package com.technical_test.ms_price.infrastructure.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@AllArgsConstructor
@Schema(description = "Generic error response")
public class ErrorResponse {

    @Schema(description = "HTTP status code", example = "400")
    private Integer status;

    @Schema(description = "Error message", example = "Invalid input parameters")
    private String message;

    @Schema(description = "Optional detailed description of the error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String details;

    @Schema(description = "Optional timestamp of when the error occurred", example = "2025-05-19T11:30:00.000Z")
    private String timestamp;

}
