package com.sniffaround.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileUploadException extends RuntimeException {
    private HttpStatus httpStatus;
    public FileUploadException(String message, HttpStatus status) {
        super(message);
    }
}
