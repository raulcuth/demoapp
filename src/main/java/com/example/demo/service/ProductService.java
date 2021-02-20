package com.example.demo.service;

import com.example.demo.domain.exception.ErrorResponse;
import com.example.demo.domain.Product;
import com.example.demo.domain.exception.ProductOperationException;
import com.example.demo.repository.ProductRepository;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author raulcuth
 */
@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        try {
            return productRepository.save(product);
        } catch (Exception ex) {
            log.error("Error while saving product: ", ex);
            throw new ProductOperationException(
                    new ErrorResponse("ERR000002", ErrorResponse.DEFAULT_MESSAGE,
                            "Failed to add product."));
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        final Optional<Product> product = productRepository.getProductById(productId);
        if (product.isPresent()) {
            return product.get();
        } else {
            throw new ProductOperationException(
                    new ErrorResponse("ERR000003", "No product.",
                            "Could not find the specified product."));
        }
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
