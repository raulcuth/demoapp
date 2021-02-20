package com.example.demo.controller.exception;

import static org.apache.logging.log4j.util.Chars.SPACE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.example.demo.domain.exception.ErrorResponse;
import com.example.demo.domain.exception.ProductOperationException;
import java.util.Iterator;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author raulcuth
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE = "Operation failed.";

    @ExceptionHandler(ProductOperationException.class)
    public ResponseEntity<ErrorResponse> handleProductOperationException(ProductOperationException e) {
        return ResponseEntity.status(BAD_REQUEST).body(e.getErrorResponse());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = buildConstraintValidationErrorMessage(e);
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse("ERR000001", DEFAULT_ERROR_MESSAGE, errorMessage));
    }

    private String buildConstraintValidationErrorMessage(ConstraintViolationException e) {
        String errorMessage = null;
        final Iterator<ConstraintViolation<?>> constraintIterator = e.getConstraintViolations().iterator();
        if (constraintIterator.hasNext()) {
            final ConstraintViolation<?> constraintViolation = constraintIterator.next();
            final String field = constraintViolation.getPropertyPath().toString();
            errorMessage = field.substring(field.lastIndexOf('.') + 1) + SPACE
                    + constraintViolation.getMessage();
        }
        return errorMessage;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse("ERR000001", "Invalid input provided.", buildErrorResponse(ex))
        );
    }

    private String buildErrorResponse(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getAllErrors().stream()
                .filter(objectError -> objectError instanceof FieldError)
                .map(FieldError.class::cast)
                .map(fieldError ->
                        String.format("field [%s] is invalid. %n", fieldError.getField()))
                .map(s -> new StringBuilder().append(s).append("Please provide valid data."))
                .collect(Collectors.joining());
    }
}
