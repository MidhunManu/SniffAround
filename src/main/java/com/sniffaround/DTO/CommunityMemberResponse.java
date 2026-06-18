package com.sniffaround.DTO;


import java.sql.Timestamp;

public record CommunityMemberResponse(
        String role,
        Timestamp joinedAt
) {
}
