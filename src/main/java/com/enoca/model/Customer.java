package com.enoca.model;


import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Customer extends BaseEntity {
    private String name;
    private String email;
    private String address;

    // Getters and Setters
}
