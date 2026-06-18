package com.sniffaround.Exception;

public class CommunityNotFoundException extends RuntimeException{
    public CommunityNotFoundException(Long id) {
        super("no community found with " + id);
    }
}
