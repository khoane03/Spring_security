package com.jwt.security.utils.exception;

import com.jwt.security.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<?>> handleAppException(AppException e) {
        return ResponseEntity.status(e.getErrorCode()
                .getHttpStatus())
                .body(ApiResponse.builder().code(e.getErrorCode().getCode())
                .message(e.getErrorCode().getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        return ApiResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ApiResponse.builder()
                .code(HttpStatus.BAD_REQUEST.value())
                .message(Objects.requireNonNull(e.getFieldError()).getDefaultMessage())
                .build();
    }
}
