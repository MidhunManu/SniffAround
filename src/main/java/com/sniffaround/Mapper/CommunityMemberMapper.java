package com.sniffaround.Mapper;

import com.sniffaround.DTO.CommunityMemberResponse;
import com.sniffaround.Model.CommunityMember;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommunityMemberMapper {
    CommunityMemberResponse toCommunityMemberResponse(CommunityMember communityMember);
}
