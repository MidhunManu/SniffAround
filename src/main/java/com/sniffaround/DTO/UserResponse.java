package com.sniffaround.DTO;

public record UserResponse(
        Long id,
        String username,
        String email,
        String avatarURL
) {
}
