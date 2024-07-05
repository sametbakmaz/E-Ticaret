package com.enoca.service;

import com.enoca.model.dto.CartDTO;
import com.enoca.model.dto.OrderItemDTO;
import com.enoca.model.Cart;
import com.enoca.model.Customer;
import com.enoca.model.OrderItem;
import com.enoca.model.Product;
import com.enoca.repository.CartRepository;
import com.enoca.repository.CustomerRepository;
import com.enoca.repository.OrderItemRepository;
import com.enoca.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public CartDTO getCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        return convertToDTO(cart);
    }

    @Transactional
    public void addProductToCart(Long customerId, Long productId, int quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart == null) {
            cart = new Cart();
            Customer customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
            cart.setCustomer(customer);
            cart.setTotalPrice(BigDecimal.ZERO);
            cart = cartRepository.save(cart); // Cart'ı önce kaydet
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock");
        }

        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(product);
        orderItem.setCart(cart);
        orderItem.setQuantity(quantity);
        orderItem.setPrice(product.getPrice());
        orderItemRepository.save(orderItem);

        cart.getItems().add(orderItem);
        updateCartTotalPrice(cart);
        cartRepository.save(cart);
    }

    @Transactional
    public void removeProductFromCart(Long customerId, Long productId, int quantity) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            // Belirtilen productId'ye sahip OrderItem'ları bul
            List<OrderItem> itemsToRemove = cart.getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .collect(Collectors.toList());

            for (OrderItem item : itemsToRemove) {
                if (item.getQuantity() > quantity) {
                    item.setQuantity(item.getQuantity() - quantity);
                    orderItemRepository.save(item);
                } else {
                    cart.getItems().remove(item);
                    orderItemRepository.delete(item);
                }
            }

            updateCartTotalPrice(cart);
            cartRepository.save(cart);
        }
    }

    @Transactional
    public void emptyCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId);
        if (cart != null) {
            cart.getItems().clear();
            cart.setTotalPrice(BigDecimal.ZERO);
            cartRepository.save(cart);
        }
    }

    private void updateCartTotalPrice(Cart cart) {
        BigDecimal totalPrice = cart.getItems().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(totalPrice);
    }

    private CartDTO convertToDTO(Cart cart) {
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setTotalPrice(cart.getTotalPrice());
        List<OrderItemDTO> items = cart.getItems().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        cartDTO.setItems(items);
        return cartDTO;
    }

    private OrderItemDTO convertToDTO(OrderItem item) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductId(item.getProduct().getId());
        orderItemDTO.setProductName(item.getProduct().getName());
        orderItemDTO.setQuantity(item.getQuantity());
        orderItemDTO.setPrice(item.getPrice());
        return orderItemDTO;
    }
}
