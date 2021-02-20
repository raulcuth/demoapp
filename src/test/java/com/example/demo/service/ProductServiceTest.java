package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.example.demo.domain.Product;
import com.example.demo.domain.exception.ProductOperationException;
import com.example.demo.repository.ProductRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author raulcuth
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    private final ProductRepository productRepository = mock(ProductRepository.class);

    private ProductService productService;

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        productService = new ProductService(productRepository);
    }

    @Test
    public void should_get_product_by_id() {
        Product returnedProduct = mockProduct();
        given(productRepository.getProductById(1L)).willReturn(Optional.of(returnedProduct));
        final Product product = productService.getProductById(1L);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isEqualTo("prodtest");
        verify(productRepository).getProductById(1L);
        verifyNoMoreInteractions(productRepository);
    }

    @Test
    public void should_fail_get_product_by_id() {
        given(productRepository.getProductById(1L)).willReturn(Optional.empty());
        expectedException.expect(ProductOperationException.class);
        productService.getProductById(1L);

    }

    private Product mockProduct() {
        Product returnedProduct = new Product();
        returnedProduct.setName("prodtest");
        returnedProduct.setQuantity(2);
        return returnedProduct;
    }
}
