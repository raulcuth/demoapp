package com.example.demo.repository;

import com.example.demo.domain.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author raulcuth
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getProductById(Long id);
}
