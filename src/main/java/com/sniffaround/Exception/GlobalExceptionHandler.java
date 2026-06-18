package com.sniffaround.Exception;

import com.sniffaround.DTO.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRefreshTokenNotFound(RefreshTokenNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
