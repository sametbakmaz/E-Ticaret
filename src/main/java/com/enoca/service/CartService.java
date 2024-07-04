package com.enoca.service;

import com.enoca.model.Cart;
import com.enoca.model.Customer;
import com.enoca.model.Product;
import com.enoca.repository.CartRepository;
import com.enoca.repository.CustomerRepository;
import com.enoca.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return cartRepository.findByCustomer(customer)
                .orElse(new Cart());
    }

    public Cart addProductToCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.getProducts().add(product);
        updateCartTotalAmount(cart);

        return cartRepository.save(cart);
    }

    public Cart removeProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        cart.getProducts().remove(product);
        updateCartTotalAmount(cart);

        return cartRepository.save(cart);
    }

    public void updateCartTotalAmount(Cart cart) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Product product : cart.getProducts()) {
            totalAmount = totalAmount.add(product.getPrice());
        }
        cart.setTotalAmount(totalAmount);
    }

    // Other cart related methods
}
