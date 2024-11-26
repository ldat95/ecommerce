package com.inditex.ecommerce.pricing.infrastructure.repository;

import com.inditex.ecommerce.pricing.domain.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataPriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByProductIdAndBrandId(Long productId, Long brandId);
}
