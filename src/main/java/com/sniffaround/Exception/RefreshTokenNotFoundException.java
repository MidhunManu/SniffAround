package com.sniffaround.Exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException() {
        super("no such token");
    }
}
