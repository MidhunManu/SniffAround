package com.sniffaround.Controller;

import com.sniffaround.DTO.*;
import com.sniffaround.Model.RefreshToken;
import com.sniffaround.Service.RefreshTokenService;
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
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        String token = this.jwtUtil.generateToken(request.username());
        RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(request.username());
        return ResponseEntity.ok(new AuthResponse(token, refreshToken.getToken()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserCreateRequest request) {
        this.userService.create(request);
        String token = this.jwtUtil.generateToken(request.username());
        RefreshToken refreshToken = this.refreshTokenService.createRefreshToken(request.username());
        return ResponseEntity.ok(new AuthResponse(token, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = this.refreshTokenService.findByToken(request.refreshToken());
        this.refreshTokenService.verifyExpiration(refreshToken);

        String newAccessToken = this.jwtUtil.generateToken(refreshToken.getUser().getUsername());
        return ResponseEntity.ok(new AuthResponse(newAccessToken, refreshToken.getToken()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {
        RefreshToken refreshToken = this.refreshTokenService.findByToken(request.refreshToken());
        this.refreshTokenService.deleteByUser(refreshToken.getUser());
        return ResponseEntity.ok("Logged out successfully");
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
