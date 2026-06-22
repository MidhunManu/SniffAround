package com.sniffaround.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

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
        LocalDate birthDate,
        @NotBlank
        @Size(max = 500)
        String bio,
        @NotBlank
        @Size(max = 100)
        MultipartFile photo
) {
}
