package com.inditex.ecommerce.pricing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inditex.ecommerce.pricing.api.dto.PriceResponseDTO;
import com.inditex.ecommerce.pricing.application.PriceService;
import com.inditex.ecommerce.pricing.domain.exceptions.PriceNotFoundException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.inditex.ecommerce.pricing.domain.Price;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public ResponseEntity<PriceResponseDTO> getPrice(
            @RequestParam Long productId,
            @RequestParam Long brandId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate) {

        Price retrievedPrice = priceService.findApplicablePrice(productId, brandId, applicationDate)
                .orElseThrow(() -> {
                    String jsonDetails = createJsonDetails(productId, brandId, applicationDate);
                    return new PriceNotFoundException("Price not found for the following data: " + jsonDetails);
                });

        PriceResponseDTO response = new PriceResponseDTO(
                retrievedPrice.getProductId(), retrievedPrice.getBrandId(), retrievedPrice.getPriceList(),
                retrievedPrice.getStartDate(), retrievedPrice.getEndDate(), retrievedPrice.getPriceValue()
        );

        return ResponseEntity.ok(response);
    }

    private String createJsonDetails(Long productId, Long brandId, LocalDateTime applicationDate) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> details = Map.of(
                "productId", productId,
                "brandId", brandId,
                "applicationDate", applicationDate.toString()
        );

        try {
            return mapper.writeValueAsString(details);
        } catch (Exception e) {
            return "It was impossible to serialize the details";
        }
    }
}
