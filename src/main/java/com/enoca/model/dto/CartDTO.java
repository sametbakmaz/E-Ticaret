package com.enoca.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDTO {
    private Long id;
    private List<OrderItemDTO> items;
    private BigDecimal totalPrice;
}
