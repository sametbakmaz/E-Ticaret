package com.enoca.service;

import com.enoca.exception.ResourceNotFoundException;
import com.enoca.model.Cart;
import com.enoca.model.Customer;
import com.enoca.model.Product;
import com.enoca.repository.CartRepository;
import com.enoca.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerService customerService;

    public Cart getCurrentCart(Customer customer) {
        return cartRepository.findByCustomer(customer)
                .orElseGet(() -> createCart(customer));
    }

    public void addProductToCart(Customer customer, Long productId) {
        Cart cart = getCurrentCart(customer);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() <= 0) {
            throw new IllegalStateException("Product is out of stock");
        }

        cart.getProducts().add(product);
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Customer customer, Long productId) {
        Cart cart = getCurrentCart(customer);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        cart.getProducts().remove(product);
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    public void emptyCart(Customer customer) {
        Cart cart = getCurrentCart(customer);
        cart.getProducts().clear();
        updateTotalPrice(cart);
        cartRepository.save(cart);
    }

    private Cart createCart(Customer customer) {
        Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setTotalPrice(0);
        return cartRepository.save(cart);
    }

    private void updateTotalPrice(Cart cart) {
        double totalPrice = cart.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
        cart.setTotalPrice(totalPrice);
    }
}
