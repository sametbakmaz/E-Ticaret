package com.enoca.service;

import com.enoca.exception.ResourceNotFoundException;
import com.enoca.model.*;
import com.enoca.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerService customerService;

    public List<Order> getAllOrdersForCustomer(Customer customer) {
        return orderRepository.findByCustomer(customer);
    }

    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

    public void placeOrder(Customer customer) {
        Cart cart = cartRepository.findByCustomer(customer)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));

        if (cart.getProducts().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setTotalPrice(cart.getTotalPrice());
        order = orderRepository.save(order);

        for (Product product : cart.getProducts()) {
            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setPriceAtPurchase(product.getPrice());
            orderProduct.setQuantity(1);
            orderProductRepository.save(orderProduct);

            product.setStock(product.getStock() - 1);
            productRepository.save(product);
        }

        cart.getProducts().clear();
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }
}
