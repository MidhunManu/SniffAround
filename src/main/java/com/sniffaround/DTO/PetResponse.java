package com.sniffaround.DTO;

import java.util.Date;

public record PetResponse(
        String name,
        String species,
        String breed,
        Date birthDate,
        String bio,
        String photoURL
) {
}
