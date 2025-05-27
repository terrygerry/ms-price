package com.technical_test.ms_price.domain.exception;

public class PriceNotFoundException extends RuntimeException {
    public PriceNotFoundException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
