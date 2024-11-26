package com.inditex.ecommerce.pricing.application;

import com.inditex.ecommerce.pricing.domain.Price;
import com.inditex.ecommerce.pricing.infrastructure.repository.JpaPriceRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    private final JpaPriceRepository priceRepository = mock(JpaPriceRepository.class);
    private final PriceService priceService = new PriceService(priceRepository);

    @Test
    void shouldReturnHighestPriorityPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price lowPriorityPrice = new Price(1L, 1L, 35455L, 1,
                LocalDateTime.of(2020, 6, 14, 0, 0, 0),
                LocalDateTime.of(2020, 12, 31, 23, 59, 0),
                BigDecimal.valueOf(25.45), 1, "EUR");

        Price highPriorityPrice = new Price(2L, 1L, 35455L, 2,
                LocalDateTime.of(2020, 6, 14, 15, 0, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30, 0),
                BigDecimal.valueOf(25.45), 2, "EUR");

        when(priceRepository.findApplicablePrice(35455L, 1L))
                .thenReturn(Arrays.asList(lowPriorityPrice, highPriorityPrice));

        Optional<Price> result = priceService.findApplicablePrice(35455L, 1L, applicationDate);

        assertTrue(result.isPresent());
        assertEquals(highPriorityPrice.getBrandId(), result.get().getBrandId());
        assertEquals(highPriorityPrice.getProductId(), result.get().getProductId());
        assertEquals(highPriorityPrice.getPriceList(), result.get().getPriceList());
        assertEquals(highPriorityPrice.getStartDate(), result.get().getStartDate());
        assertEquals(highPriorityPrice.getEndDate(), result.get().getEndDate());
        assertEquals(highPriorityPrice.getPriority(), result.get().getPriority());
        assertEquals(highPriorityPrice.getPriceValue(), result.get().getPriceValue());
        assertEquals(highPriorityPrice.getCurrency(), result.get().getCurrency());
    }
}
