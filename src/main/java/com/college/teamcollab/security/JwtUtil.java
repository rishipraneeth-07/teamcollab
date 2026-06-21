package com.college.teamcollab.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "qwertyuiopasdfghjklzxcvbnm";
    private static final long EXPIRATION_TIME = 86400000;
    public String generateToken(String email){
        Date now = new Date();
        Date expiryDate = new Date(
                now.getTime() + EXPIRATION_TIME);
        SecretKey key = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );
        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }
}
