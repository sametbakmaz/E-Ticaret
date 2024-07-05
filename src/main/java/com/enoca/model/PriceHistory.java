package com.enoca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class PriceHistory extends BaseEntity {
    @ManyToOne
    private Product product;

    private BigDecimal price;
    private int quantity;
    private LocalDateTime recordedAt = LocalDateTime.now();
}
