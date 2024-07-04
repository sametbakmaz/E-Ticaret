package com.enoca.controller;

import com.enoca.model.Order;
import com.enoca.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{customerId}/{cartId}")
    public String placeOrder(@PathVariable Long customerId, @PathVariable Long cartId) {
        orderService.placeOrder(customerId, cartId);
        return "redirect:/orders";
    }

    @GetMapping
    public String getAllOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/{orderId}")
    public String getOrderForCode(@PathVariable Long orderId, Model model) {
        Order order = orderService.getOrderForCode(orderId);
        model.addAttribute("order", order);
        return "orderDetails";
    }
}
