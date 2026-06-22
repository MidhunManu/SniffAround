package com.sniffaround.Service;

import com.sniffaround.Exception.RefreshTokenNotFoundException;
import com.sniffaround.Exception.UserNotFoundException;
import com.sniffaround.Model.RefreshToken;
import com.sniffaround.Model.User;
import com.sniffaround.Repository.RefreshTokenRepository;
import com.sniffaround.Repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Data
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    @Value("${jwt.refresh-expiration}")
    private long refreshExpiration;

    @Transactional
    public RefreshToken createRefreshToken(String username) {
        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        this.refreshTokenRepository.deleteByUser(user);
        this.refreshTokenRepository.flush();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setExpiresAt(Instant.now().plusMillis(this.refreshExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());

        return this.refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (refreshToken.getExpiresAt().isBefore(Instant.now())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "token expired, please restart");
        }
        return refreshToken;
    }

    public RefreshToken findByToken(String token) {
        return this.refreshTokenRepository.findByToken(token).orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Transactional
    public void deleteByUser(User user) {
        this.refreshTokenRepository.deleteByUser(user);
    }
}
