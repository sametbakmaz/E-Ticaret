package com.enoca.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private double priceAtPurchase;
    private int quantity;
}
