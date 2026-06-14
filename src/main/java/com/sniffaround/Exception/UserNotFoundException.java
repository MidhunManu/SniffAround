package com.sniffaround.Exception;

import lombok.AllArgsConstructor;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("No user found with Id " + id);
    }
}
