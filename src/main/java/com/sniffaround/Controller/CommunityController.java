package com.sniffaround.Controller;

import com.sniffaround.DTO.CommunityMemberResponse;
import com.sniffaround.DTO.CommunityResponse;
import com.sniffaround.DTO.CreateCommunityRequest;
import com.sniffaround.DTO.UpdateCommunityRequest;
import com.sniffaround.Service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/communities")
@AllArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @GetMapping
    public ResponseEntity<List<CommunityResponse>> index() {
        return ResponseEntity.ok(this.communityService.index());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityResponse> show(@PathVariable Long id) {
        return ResponseEntity.ok(this.communityService.show(id));
    }

    @PostMapping
    public ResponseEntity<CommunityResponse> create(@RequestBody CreateCommunityRequest request) {
        return ResponseEntity.ok(this.communityService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommunityResponse> update(@PathVariable Long id, @RequestBody UpdateCommunityRequest request) {
        return ResponseEntity.ok(this.communityService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.communityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/join/{id}")
    public ResponseEntity<CommunityMemberResponse> joinCommunity(@PathVariable Long id) {
        return ResponseEntity.ok(this.communityService.joinCommunity(id));
    }

    @DeleteMapping("/leave/{id}")
    public ResponseEntity<Void> leaveCommunity(@PathVariable Long id) {
        this.communityService.leaveCommunity(id);
        return ResponseEntity.noContent().build();
    }
}
