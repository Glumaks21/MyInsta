package ua.glumaks.rest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.glumaks.rest.model.User;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import static ua.glumaks.rest.security.SecurityConstants.EXPIRATION_TIME;
import static ua.glumaks.rest.security.SecurityConstants.SECRET;

@Slf4j
@Component
public class JWTService {

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
        return Long.parseLong(extractSubject(token));
    }

}
