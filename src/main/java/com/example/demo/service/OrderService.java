package com.example.demo.service;

import com.example.demo.domain.OrderCart;
import com.example.demo.domain.Product;
import com.example.demo.repository.OrderRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author raulcuth
 */
@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderCart addNewOrder(OrderCart orderCart) {
        return orderRepository.save(orderCart);
    }

    public List<OrderCart> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderCart addProductToOrder(Long orderId, Product product) {
        OrderCart order = orderRepository.getOne(orderId);
        order.getProducts().add(product);
        return orderRepository.save(order);
    }
}
