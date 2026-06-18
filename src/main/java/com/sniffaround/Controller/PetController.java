package com.sniffaround.Controller;

import com.sniffaround.DTO.PetCreateRequest;
import com.sniffaround.DTO.PetResponse;
import com.sniffaround.DTO.PetUpdateRequest;
import com.sniffaround.Service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/pets")
public class PetController {
    private final PetService petService;

    @GetMapping
    public ResponseEntity<List<PetResponse>> index() {
        return ResponseEntity.ok(this.petService.index());
    }

    @PostMapping
    public ResponseEntity<PetResponse> create(@RequestBody PetCreateRequest request) {
        return ResponseEntity.ok(this.petService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PetResponse> show(@PathVariable Long id) {
        return ResponseEntity.ok(this.petService.show(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.petService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PetResponse> update(@PathVariable Long id, @RequestBody PetUpdateRequest request) {
        return ResponseEntity.ok(this.petService.update(id, request));
    }
}
