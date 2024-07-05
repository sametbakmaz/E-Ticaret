package com.enoca.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class OrderItem extends BaseEntity {

    @ManyToOne
    private Product product;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Order order;

    private int quantity;
    private BigDecimal price;
}
