package com.enoca.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Product extends BaseEntity{

    private String name;
    private BigDecimal price;
    private int stock;
}
