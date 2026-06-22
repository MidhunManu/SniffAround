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

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePetNotFoundException(PetNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND.value(), Instant.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserAlreadyJoinedCommunityException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyJoinedCommunityException(UserAlreadyJoinedCommunityException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        e.getMessage(),
                        HttpStatus.CONFLICT.value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(UserNotCommunityMemberException.class)
    public ResponseEntity<ErrorResponse> handleUserNotCommunityMemberException(UserNotCommunityMemberException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorResponse(
                        e.getMessage(),
                        HttpStatus.FORBIDDEN.value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(OwnershipTransferRequiredException.class)
    public ResponseEntity<ErrorResponse> handleOwnershipTransferRequiredException(OwnershipTransferRequiredException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        e.getMessage(),
                        HttpStatus.CONFLICT.value(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> handleFileUploadException(FileUploadException e) {
        HttpStatus httpStatus = e.getHttpStatus();
        return switch (httpStatus) {
            case UNPROCESSABLE_CONTENT, SERVICE_UNAVAILABLE, CONTENT_TOO_LARGE, INSUFFICIENT_STORAGE ->
                ResponseEntity
                        .status(e.getHttpStatus())
                        .body(new ErrorResponse(
                                e.getMessage(),
                                e.getHttpStatus().value(),
                                Instant.now()
                        ));
            default -> ResponseEntity.internalServerError().build();
        };
    }
}
