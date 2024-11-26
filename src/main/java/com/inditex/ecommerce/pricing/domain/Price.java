package com.inditex.ecommerce.pricing.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRAND_ID")
    private Long brandId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "PRICE_LIST")
    private Integer priceList;

    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE")
    private BigDecimal priceValue;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "CURRENCY")
    private String currency;

    public boolean isValidFor(LocalDateTime date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
