package com.enoca.model;

import lombok.Data;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {
    @OneToOne
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    private BigDecimal totalPrice;
}
