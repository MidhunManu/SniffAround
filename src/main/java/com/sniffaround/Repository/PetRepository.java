package com.sniffaround.Repository;

import com.sniffaround.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserId(Long id);
    Optional<Pet> findByIdAndUser_Id(Long id, Long userId);
}
