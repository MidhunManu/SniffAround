package com.sniffaround.Controller;

import com.sniffaround.Annotation.CheckOwnership;
import com.sniffaround.DTO.PetCreateRequest;
import com.sniffaround.DTO.PetResponse;
import com.sniffaround.DTO.PetUpdateRequest;
import com.sniffaround.Enum.ResourceTypeEnum;
import com.sniffaround.Service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<PetResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size
    ) {
        return ResponseEntity.ok(this.petService.index(page, size));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PetResponse> create(@ModelAttribute PetCreateRequest request) throws Exception {
        return ResponseEntity.ok(this.petService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> show(@PathVariable Long id) {
        return ResponseEntity.ok(this.petService.show(id));
    }

    @CheckOwnership(ResourceTypeEnum.PET)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @CheckOwnership(ResourceTypeEnum.PET)
    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> update(@PathVariable Long id, @RequestBody PetUpdateRequest request) {
        return ResponseEntity.ok(this.petService.update(id, request));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PetResponse>> showMyPets() {
        return ResponseEntity.ok(this.petService.showMyPets());
    }
}
