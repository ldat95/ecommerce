package com.inditex.ecommerce.pricing.infrastructure.repository;

import com.inditex.ecommerce.pricing.domain.Price;
import com.inditex.ecommerce.pricing.domain.repository.PriceRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaPriceRepository implements PriceRepository {

    private final SpringDataPriceRepository springDataPriceRepository;

    public JpaPriceRepository(SpringDataPriceRepository springDataPriceRepository) {
        this.springDataPriceRepository = springDataPriceRepository;
    }


    public List<Price> findApplicablePrice(Long productId, Long brandId){
        return springDataPriceRepository.findByProductIdAndBrandId(productId, brandId);
    }
}
