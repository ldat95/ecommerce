package com.inditex.ecommerce.pricing.api.record;

public record PriceTestCase(
        String productId,
        String brandId,
        String applicationDate,
        String expectedPriceList,
        String expectedPrice
) {}
