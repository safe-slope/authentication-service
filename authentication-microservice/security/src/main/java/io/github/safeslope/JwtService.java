package io.github.safeslope;

import io.github.safeslope.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    private final JwtKeyProvider jwtKeyProvider;

    public JwtService(JwtKeyProvider jwtKeyProvider) {
        this.jwtKeyProvider = jwtKeyProvider;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        if (user.getTenant() != null) {
            claims.put("tenantId", user.getTenant().getId());
        }
        return createToken(claims, String.valueOf(user.getId()));
    }

    private String createToken(Map<String, Object> claims, String userId) {
        return Jwts.builder()
                .claims(claims)
                .subject(userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey())
                .compact();
    }

    private Key getSignKey() {
        return jwtKeyProvider.privateKey();
    }

    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Integer extractTenantId(String token) {
        Claims claims = extractAllClaims(token);
        Object tenantId = claims.get("tenantId");
        if (tenantId == null) {
            return null;
        }
        if (tenantId instanceof Integer) {
            return (Integer) tenantId;
        }
        try {
            return Integer.valueOf(tenantId.toString());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith((java.security.PublicKey) getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, User user) {
        try {
            final String userId = extractUserId(token);
            return (userId.equals(String.valueOf(user.getId())) && !isTokenExpired(token));
        } catch (Exception e) {
            // Token parsing failed or is malformed
            return false;
        }
    }
}
