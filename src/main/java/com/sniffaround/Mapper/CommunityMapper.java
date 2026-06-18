package com.sniffaround.Mapper;

import com.sniffaround.DTO.CommunityResponse;
import com.sniffaround.DTO.CreateCommunityRequest;
import com.sniffaround.DTO.UpdateCommunityRequest;
import com.sniffaround.Model.Community;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommunityMapper {
    Community toCommunityFromCreateDTO(CreateCommunityRequest request);
    CommunityResponse toCommunityResponse(Community community);
    void updateCommunityFromDTO(UpdateCommunityRequest request, @MappingTarget Community community);
}
