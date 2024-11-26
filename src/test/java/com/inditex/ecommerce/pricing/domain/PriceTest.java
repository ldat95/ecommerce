package com.inditex.ecommerce.pricing.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void testIsValidFor_DateIsBeforeStartDate_ReturnsFalse() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 15, 10, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 20, 10, 0, 0, 0);
        Price price = new Price(1L, 1L, 35455L, 1, startDate, endDate, BigDecimal.valueOf(34.45), 1, "EUR");

        LocalDateTime testDate = LocalDateTime.of(2020, 6, 14, 10, 0, 0, 0);
        assertFalse(price.isValidFor(testDate));
    }

    @Test
    void testIsValidFor_DateIsAfterEndDate_ReturnsFalse() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 15, 10, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 20, 10, 0, 0, 0);
        Price price = new Price(1L, 1L, 35455L, 1, startDate, endDate, BigDecimal.valueOf(100.0), 1, "EUR");

        LocalDateTime testDate = LocalDateTime.of(2020, 6, 21, 10, 0, 0, 0);
        assertFalse(price.isValidFor(testDate));
    }

    @Test
    void testIsValidFor_DateIsBetweenStartDateAndEndDate_ReturnsTrue() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 15, 10, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2020, 6, 20, 10, 0, 0, 0);
        Price price = new Price(1L, 1L, 35455L, 1, startDate, endDate, BigDecimal.valueOf(100.0), 1, "EUR");

        LocalDateTime testDate = LocalDateTime.of(2020, 6, 18, 10, 0, 0, 0);
        assertTrue(price.isValidFor(testDate));
    }
}

