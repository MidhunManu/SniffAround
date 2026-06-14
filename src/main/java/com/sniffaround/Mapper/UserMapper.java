package com.sniffaround.Mapper;

import com.sniffaround.DTO.UserCreateRequest;
import com.sniffaround.DTO.UserResponse;
import com.sniffaround.DTO.UserUpdateRequest;
import com.sniffaround.Model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    void updateUserFromDTO(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
    UserResponse toUserResponse(User user);
    List<UserResponse> toUserResponseList(List<User> users);
    User toUser(UserCreateRequest userCreateRequest);
}
