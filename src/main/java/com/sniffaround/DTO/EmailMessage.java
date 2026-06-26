package com.sniffaround.DTO;

public record EmailMessage(
        String to,
        String subject,
        String template
) {
}
