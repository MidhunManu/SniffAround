package com.sniffaround.Exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("No user found with Id " + id);
    }

    public UserNotFoundException(String username) {
        super("No user found with Id " + username);
    }
}
