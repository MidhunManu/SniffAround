package com.sniffaround.Exception;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(Long id) {
        super("no pet found with id " + id);
    }
}
