package com.example.demo.controller;

import com.example.demo.domain.OrderCart;
import com.example.demo.domain.Product;
import com.example.demo.service.OrderService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author raulcuth
 */
@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/order")
    public ResponseEntity<OrderCart> addOrder(@Valid @RequestBody OrderCart orderCart) {
        return ResponseEntity.ok(orderService.addNewOrder(orderCart));
    }

    @GetMapping(value = "/order")
    public ResponseEntity<List<OrderCart>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping(value = "/order/{orderId}")
    public ResponseEntity<OrderCart> addProductToOrder(@PathVariable Long orderId, @RequestBody Product product) {
        return ResponseEntity.ok(orderService.addProductToOrder(orderId, product));
    }
}
