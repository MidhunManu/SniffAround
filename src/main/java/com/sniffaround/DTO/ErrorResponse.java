package com.sniffaround.DTO;

import java.time.Instant;

public record ErrorResponse(
        String message,
        int statusCode,
        Instant instant
) {
}
