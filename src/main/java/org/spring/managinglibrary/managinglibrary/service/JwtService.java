package org.spring.managinglibrary.managinglibrary.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.spring.managinglibrary.managinglibrary.model.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collections;
import java.util.Collection;

import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JwtService {

    private final JwtParser jwtParser;
    private final SecretKey secretKey;
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public JwtService(@Value("${jwt.secret}") String secretKey) {
        byte[] decodedKey;
        try {
            decodedKey = Decoders.BASE64.decode(secretKey);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid Base64-encoded jwt.secret. Using raw bytes as fallback.");
            decodedKey = secretKey.getBytes();
        }
        if (decodedKey.length * 8 < 256) {
            throw new IllegalArgumentException("The secret key must be at least 256 bits.");
        }
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(this.secretKey).build();
    }

    public String generateToken(Member member) {
        return Jwts.builder()
                .setSubject(member.getEmail())
                .claim("role", member.getRole().name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private Claims extractClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Collection<? extends GrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractClaims(token);
        String role = claims.get("role", String.class);
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role));
    }

    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            logger.error("Invalid JWT token", e);
            return false;
        }
    }

    public String extractUsername(String token) {
        Claims claims = jwtParser.parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
