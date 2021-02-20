package com.example.demo.domain.exception;

import lombok.Getter;

/**
 * @author raulcuth
 */
@Getter
public class ProductOperationException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public ProductOperationException(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
