package com.product.promotion.utils;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiError {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime localDateTime;
    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;

    private ApiError() {
        localDateTime = LocalDateTime.now();
    }

    public ApiError(HttpStatus httpStatus, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus httpStatus, String message, Throwable ex) {
        this(httpStatus, ex);
        this.message = message;
    }

}
