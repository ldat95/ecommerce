package com.inditex.ecommerce.pricing.domain.exceptions;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(final String message) {
        super(message);
    }
}
