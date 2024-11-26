package com.inditex.ecommerce.pricing.api;

import com.inditex.ecommerce.pricing.domain.exceptions.PriceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void handlePriceNotFound_ReturnsNotFoundResponse(){
        String errorMessage = "Price not found for the given parameters";
        PriceNotFoundException priceNotFoundException = new PriceNotFoundException(errorMessage);

        ResponseEntity<String> response = globalExceptionHandler.handlePriceNotFound(priceNotFoundException);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }

    @Test
    void handleGenericException_ReturnsInternalServerErrorResponse(){
        String errorMessage = "Internal Server Error";
        Exception exception = new Exception(errorMessage);

        ResponseEntity<String> response = globalExceptionHandler.handleGenericException(exception);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(errorMessage, response.getBody());
    }
}
