package com.enoca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class PriceHistory extends BaseEntity {
    @ManyToOne
    private Product product;

    private BigDecimal price;
    private LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        super.onCreate();
        this.date = LocalDateTime.now();
    }
}
