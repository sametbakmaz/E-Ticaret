package com.enoca.controller;

import com.enoca.model.Customer;
import com.enoca.service.OrderService;
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
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String viewOrders(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Customer customer = customerService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
        model.addAttribute("orders", orderService.getAllOrdersForCustomer(customer));
        return "orders";
    }

    @GetMapping("/{orderId}")
    public String viewOrderDetails(@PathVariable Long orderId, Model model) {
        model.addAttribute("order", orderService.getOrderById(orderId));
        return "orderDetails";
    }

    @PostMapping("/place")
    public String placeOrder(@AuthenticationPrincipal UserDetails userDetails) {
        Customer customer = customerService.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
        orderService.placeOrder(customer);
        return "redirect:/orders";
    }
}
