package com.sniffaround.Controller;

import com.sniffaround.Annotation.CheckOwnership;
import com.sniffaround.DTO.UserCreateRequest;
import com.sniffaround.DTO.UserResponse;
import com.sniffaround.DTO.UserUpdateRequest;
import com.sniffaround.Model.User;
import com.sniffaround.Service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size
    ) {
        var users = this.userService.index(page, size);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> show(@PathVariable Long id) {
        var user = this.userService.show(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @CheckOwnership
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserUpdateRequest request) {
        var user = this.userService.update(id, request);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
