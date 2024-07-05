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
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

    @PostMapping
    public String registerUser(Customer customer) {
        customerService.createCustomer(customer);
        return "redirect:/login";
    }
}
