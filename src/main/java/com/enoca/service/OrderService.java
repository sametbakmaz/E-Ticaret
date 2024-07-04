package com.enoca.service;

import com.enoca.model.*;
import com.enoca.repository.CartRepository;
import com.enoca.repository.CustomerRepository;
import com.enoca.repository.OrderRepository;
import com.enoca.repository.PriceHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    public Order placeOrder(Long customerId, Long cartId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(cart.getProducts());
        order.setTotalAmount(cart.getTotalAmount());
        order.setOrderDate(LocalDateTime.now());

        orderRepository.save(order);

        for (Product product : order.getProducts()) {
            PriceHistory priceHistory = new PriceHistory();
            priceHistory.setProduct(product);
            priceHistory.setPrice(product.getPrice());
            priceHistory.setQuantity(product.getStock());
            priceHistory.setRecordedAt(LocalDateTime.now());
            priceHistoryRepository.save(priceHistory);
        }

        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderForCode(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // Diğer sipariş işlemleri
}
