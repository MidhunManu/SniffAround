package com.sniffaround.DTO;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
        @NotBlank(message = "username is required")
        String username,
        @NotBlank(message = "email must not be null")
        String email,
        @NotBlank(message = "password must not be null")
        String password,
        String avatarURL
) {
}
