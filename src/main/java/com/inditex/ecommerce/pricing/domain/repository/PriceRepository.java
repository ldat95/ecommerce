package com.inditex.ecommerce.pricing.domain.repository;

import com.inditex.ecommerce.pricing.domain.Price;

import java.util.List;

public interface PriceRepository {
    List<Price> findApplicablePrice(Long productId, Long brandId);
}
