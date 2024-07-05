package com.enoca.controller;

import com.enoca.exception.ResourceNotFoundException;
import com.enoca.model.Product;
import com.enoca.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index";
    }
}
