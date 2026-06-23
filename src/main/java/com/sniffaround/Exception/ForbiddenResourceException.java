package com.sniffaround.Exception;

public class ForbiddenResourceException extends RuntimeException {
    public ForbiddenResourceException() {
        super("You do not have permission to access this resource");
    }
}
