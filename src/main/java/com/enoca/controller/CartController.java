package com.enoca.controller;


import com.enoca.model.Cart;
import com.enoca.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{customerId}")
    public String getCart(@PathVariable Long customerId, Model model) {
        Cart cart = cartService.getCart(customerId);
        model.addAttribute("cart", cart);
        return "carts";
    }

    @PostMapping("/{cartId}/addProduct/{productId}")
    public String addProductToCart(@PathVariable Long cartId, @PathVariable Long productId) {
        cartService.addProductToCart(cartId, productId);
        return "redirect:/carts/" + cartId;
    }

    @PostMapping("/{cartId}/removeProduct/{productId}")
    public String removeProductFromCart(@PathVariable Long cartId, @PathVariable Long productId) {
        cartService.removeProductFromCart(cartId, productId);
        return "redirect:/carts/" + cartId;
    }
}
