package com.example.demo.repository;

import com.example.demo.domain.OrderCart;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author raulcuth
 */
public interface OrderRepository extends JpaRepository<OrderCart, Long> {

}
