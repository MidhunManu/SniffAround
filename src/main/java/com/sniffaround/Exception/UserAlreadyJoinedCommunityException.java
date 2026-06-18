package com.sniffaround.Exception;

public class UserAlreadyJoinedCommunityException extends RuntimeException {
    public UserAlreadyJoinedCommunityException(Long id) {
        super("User has already joined the community " + id);
    }
}
