package com.sniffaround.DTO;

public record UpdateCommunityRequest (
        String name,
        String city,
        String areaTag,
        String description
){
}
