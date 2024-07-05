package com.enoca.controller;

import com.enoca.model.Customer;
import com.enoca.service.CartService;
import com.enoca.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String viewCart(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Customer customer = customerService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
        model.addAttribute("cart", cartService.getCurrentCart(customer));
        return "cart";
    }

    @PostMapping("/add/{productId}")
    public String addProductToCart(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        Customer customer = customerService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
        cartService.addProductToCart(customer, productId);
        return "redirect:/cart";
    }

    @PostMapping("/remove/{productId}")
    public String removeProductFromCart(@PathVariable Long productId, @AuthenticationPrincipal UserDetails userDetails) {
        Customer customer = customerService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
        cartService.removeProductFromCart(customer, productId);
        return "redirect:/cart";
    }
}
