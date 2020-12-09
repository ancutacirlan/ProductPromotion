package com.product.promotion.utils;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ResponseException  extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
    return new ResponseEntity<>(apiError, apiError.getHttpStatus());
}

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON request", ex)
        );
    }

    @ExceptionHandler(javax.persistence.EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
        return buildResponseEntity(
                new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex)
        );
    }

    @ExceptionHandler(InvalidException.class)
    protected ResponseEntity<Object> handleEntityInvalid(InvalidException ex) {
        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ex)
        );
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST,
                        "The supplied data triggers a database constraint violation.", ex)
        );
    }

    @ExceptionHandler(IOException.class)
    protected ResponseEntity<Object> handleInputOutputException(IOException ex) {
        return buildResponseEntity(
                new ApiError(HttpStatus.BAD_REQUEST,
                        "There is a problem with the files for this execution", ex)
        );
    }
}
