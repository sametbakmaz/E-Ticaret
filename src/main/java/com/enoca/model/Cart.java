package com.enoca.model;

import lombok.Data;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {
    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToMany
    @JoinTable(
            name = "cart_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    private double totalPrice;
}
