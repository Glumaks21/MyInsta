package com.example.myinsta.security;

import com.example.myinsta.entity.User;
import com.example.myinsta.exceptions.JwtServiceException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static com.example.myinsta.security.SecurityConstants.EXPIRATION_TIME;
import static com.example.myinsta.security.SecurityConstants.SECRET;

@Slf4j
@Component
public class JwtService {

    public String generateToken(User user) {
        Map<String, Object> claimsMap = getExtraClaimsFor(user);
        Date issueDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        try {
            return Jwts.builder()
                    .addClaims(claimsMap)
                    .setSubject(user.getId().toString())
                    .setIssuedAt(issueDate)
                    .setExpiration(expireDate)
                    .signWith(getSignKey(), SignatureAlgorithm.HS256)
                    .compact();
        } catch (InvalidKeyException e) {
            throw new JwtServiceException("Couldn't to generate sign key", e);
        }
    }

    private Map<String, Object> getExtraClaimsFor(User user) {
        return Map.of(
                "username", user.getUsername(),
                "firstname", user.getName(),
                "surname", user.getSurname()
        );
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean isValid(String token, User user) {
        Long userId = extractUserId(token);
        return user.getId().equals(userId) && !isExpired(token);
    }

    public boolean isExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            log.warn("Couldn't to parse jwt: " + token, e);
            throw new JwtServiceException(e);
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractUserId(String token) {
        return extractClaim(token,
                claims -> claims.get("userId", Long.class)
        );
    }

}
