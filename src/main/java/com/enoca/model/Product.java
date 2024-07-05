package com.enoca.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    private String name;
    private String description;
    private double price;
    private int stock;
}
