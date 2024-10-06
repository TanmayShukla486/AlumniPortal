package org.ietdavv.alumni_portal.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.entity.Role;
import org.ietdavv.alumni_portal.service.interfaces.JwtServiceInterface;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements JwtServiceInterface {

    private final String secretKey;

    public JwtService()  {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey key = keyGenerator.generateKey();
            secretKey = Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    @Override
    public Date getExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    @Override
    public boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    @Override
    public SecretKey getSecretKey() {
        byte[] encoded = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(encoded);
    }

    @Override
    public String generateToken(PortalUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .and()
                .signWith(getSecretKey())
                .compact();
    }


}
