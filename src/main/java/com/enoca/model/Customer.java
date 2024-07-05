package com.enoca.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    private String name;
    private String email;
}
