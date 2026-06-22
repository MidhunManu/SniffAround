package com.sniffaround.Service;

import com.sniffaround.DTO.PetCreateRequest;
import com.sniffaround.DTO.PetResponse;
import com.sniffaround.DTO.PetUpdateRequest;
import com.sniffaround.Exception.PetNotFoundException;
import com.sniffaround.Exception.UserNotFoundException;
import com.sniffaround.Mapper.PetMapper;
import com.sniffaround.Model.Pet;
import com.sniffaround.Model.User;
import com.sniffaround.Repository.PetRepository;
import com.sniffaround.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;
    private final PetMapper petMapper;
    private final MinioService minioService;

    public List<PetResponse> index(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return this.petRepository
                .findAll(pageable)
                .stream()
                .map(this.petMapper::toPetResponse)
                .toList();
    }

    public PetResponse show(Long id) {
        return this.petMapper.toPetResponse(this.petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id)));
    }

    public PetResponse create(PetCreateRequest request) {
        String ownerName = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                        .getName();

        User owner = this.userRepository.findByUsername(ownerName)
                .orElseThrow(() -> new UserNotFoundException(ownerName));

        Pet pet = this.petMapper.toPet(request);
        String key = this.minioService.uploadPublicFile(request.photo());
        String photoURL = this.minioService.getPublicUrl(key);
        pet.setPhotoURL(photoURL);
        pet.setUser(owner);
        pet.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return this.petMapper.toPetResponse(this.petRepository.save(pet));
    }

    public void delete(Long id) {
        this.petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        this.petRepository.deleteById(id);
    }

    public PetResponse update(Long id, PetUpdateRequest request) {
        Pet pet = this.petRepository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
        this.petMapper.updatePetFromDto(request, pet);
        this.petRepository.save(pet);
        return this.petMapper.toPetResponse(pet);
    }
}
