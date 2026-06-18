package com.sniffaround.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public record PetCreateRequest(
        @NotBlank
        @Size(max = 100)
        String name,
        @NotBlank
        @Size(max = 100)
        String species,
        @NotBlank
        @Size(max = 100)
        String breed,
        Date birthDate,
        @NotBlank
        @Size(max = 500)
        String bio,
        @NotBlank
        @Size(max = 100)
        String photoURL
) {
}
