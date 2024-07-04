package com.enoca.controller;


import com.enoca.model.Customer;
import com.enoca.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public String getAllCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers";
    }

    @PostMapping("/customers")
    public String addCustomer(Customer customer) {
        customerService.addCustomer(customer);
        return "redirect:/customers";
    }
}
