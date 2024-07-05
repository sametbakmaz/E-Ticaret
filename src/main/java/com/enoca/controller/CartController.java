package com.enoca.controller;


import com.enoca.model.dto.CartDTO;
import com.enoca.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("/{customerId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Long customerId) {
        return ResponseEntity.ok(cartService.getCart(customerId));
    }

    @PostMapping("/{customerId}/add/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable Long customerId, @PathVariable Long productId, @RequestParam int quantity) {
        cartService.addProductToCart(customerId, productId, quantity);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable Long customerId, @PathVariable Long productId) {
        cartService.removeProductFromCart(customerId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{customerId}/empty")
    public ResponseEntity<Void> emptyCart(@PathVariable Long customerId) {
        // Implement this method in CartService to clear all items in the cart and reset total price
        cartService.emptyCart(customerId);
        return ResponseEntity.ok().build();
    }
}
