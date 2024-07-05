package com.enoca.controller;


import com.enoca.model.PriceHistory;
import com.enoca.model.dto.OrderDTO;
import com.enoca.repository.PriceHistoryRepository;
import com.enoca.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderDTO>> getAllOrdersForCustomer(@PathVariable Long customerId) {
        return ResponseEntity.ok(orderService.getAllOrdersForCustomer(customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderForCode(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderForCode(orderId));
    }

    @PostMapping("/place/{customerId}")
    public ResponseEntity<Void> placeOrder(@PathVariable Long customerId) {
        orderService.placeOrder(customerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/history/{productId}")
    public ResponseEntity<List<PriceHistory>> getPriceHistory(@PathVariable Long productId) {
        return ResponseEntity.ok(priceHistoryRepository.findByProductId(productId));
    }
}
