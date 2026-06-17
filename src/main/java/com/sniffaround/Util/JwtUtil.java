package com.sniffaround.Util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")  // ← correct
    private String SECRET;
    @Value("${jwt.expiration}")
    private long EXPIRATION;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }
}
