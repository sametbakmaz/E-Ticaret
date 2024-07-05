package com.enoca.controller;

import com.enoca.model.Customer;
import com.enoca.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(Customer customer) {
        customerService.addCustomer(customer);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
