package com.enoca.model;


import jakarta.persistence.*;
import lombok.Data;


import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Cart extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "cart_product",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    private BigDecimal totalAmount = BigDecimal.ZERO;

    // Getters and Setters
}
