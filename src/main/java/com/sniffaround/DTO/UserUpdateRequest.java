package com.sniffaround.DTO;

public record UserUpdateRequest(
        String username,
        String email,
        String password,
        String avatarURL
) {
}
