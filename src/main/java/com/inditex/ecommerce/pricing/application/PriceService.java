package com.inditex.ecommerce.pricing.application;

import com.inditex.ecommerce.pricing.domain.Price;
import com.inditex.ecommerce.pricing.domain.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    public Optional<Price> findApplicablePrice(Long productId, Long brandId, LocalDateTime applicationDate) {
        return priceRepository.findApplicablePrice(productId, brandId).stream()
                .filter(price -> price.isValidFor(applicationDate))
                .max(Comparator.comparingInt(Price::getPriority));
    }
}
