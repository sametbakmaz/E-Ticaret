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
    public ResponseEntity<CartDTO> getCart(@PathVariable("customerId") Long customerId) {
        return ResponseEntity.ok(cartService.getCart(customerId));
    }

    @PostMapping("/{customerId}/add/{productId}")
    public ResponseEntity<Void> addProductToCart(@PathVariable("customerId") Long customerId,
                                                 @PathVariable("productId") Long productId,
                                                 @RequestParam("quantity") int quantity) {
        cartService.addProductToCart(customerId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/remove/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@PathVariable("customerId") Long customerId,
                                                      @PathVariable("productId") Long productId,
                                                      @RequestParam("quantity") int quantity) {
        cartService.removeProductFromCart(customerId, productId, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{customerId}/empty")
    public ResponseEntity<Void> emptyCart(@PathVariable("customerId") Long customerId) {
        cartService.emptyCart(customerId);
        return ResponseEntity.noContent().build();
    }
}
