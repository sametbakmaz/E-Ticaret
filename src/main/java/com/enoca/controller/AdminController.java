package com.enoca.controller;


import com.enoca.exception.ResourceNotFoundException;
import com.enoca.model.Product;
import com.enoca.service.CustomerService;
import com.enoca.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/products")
    public String manageProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @GetMapping("/products/new")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "admin/create_product";
    }

    @PostMapping("/products")
    public String createProduct(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        model.addAttribute("product", product);
        return "admin/edit_product";
    }

    @PostMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product productDetails) {
        productService.updateProduct(id, productDetails);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/customers")
    public String manageCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "admin/customers";
    }

    @GetMapping("/customers/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/admin/customers";
    }
}
