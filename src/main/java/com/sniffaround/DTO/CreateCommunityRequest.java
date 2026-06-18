package com.sniffaround.DTO;

public record CreateCommunityRequest (
        String name,
        String city,
        String areaTag,
        String description
) {

}
