package com.sniffaround.Controller;

import com.sniffaround.DTO.AuthRequest;
import com.sniffaround.DTO.UserCreateRequest;
import com.sniffaround.DTO.UserResponse;
import com.sniffaround.Service.UserService;
import com.sniffaround.Util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequest request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        String token = this.jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody UserCreateRequest request) {
        this.userService.create(request);
        String token = this.jwtUtil.generateToken(request.username());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return ResponseEntity.ok(this.userService.findByName(username));
    }
}
