package com.sniffaround.Exception;

public class UserNotCommunityMemberException extends RuntimeException {
    public UserNotCommunityMemberException() {
        super("You are not a member in this community");
    }
}
