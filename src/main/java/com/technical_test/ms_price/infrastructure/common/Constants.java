package com.technical_test.ms_price.infrastructure.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String MESSAGE_RESOURCE_NOT_FOUND_EXCEPTION = "No se encontro el recurso solicitado.";
    public static final String DATEFROM_PARAMETER_NAME = "dateFrom";
    public static final String MESSAGE_DATEFROM_CORRECT_FORMAT = "El dateFrom debe tener este formato yyyy-MM-dd-HH.mm.ss";
}
