package com.sniffaround.Mapper;

import com.sniffaround.DTO.PetCreateRequest;
import com.sniffaround.DTO.PetResponse;
import com.sniffaround.DTO.PetUpdateRequest;
import com.sniffaround.Model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PetMapper {
    Pet toPet(PetCreateRequest request);
    PetResponse toPetResponse(Pet pet);
    void updatePetFromDto(PetUpdateRequest petUpdateRequest, @MappingTarget Pet pet);
}
