package org.ietdavv.alumni_portal.service.interfaces;

import io.jsonwebtoken.Claims;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

public interface JwtServiceInterface {

    Claims extractAllClaims(String token);
    String extractUsername(String token);
    String extractRole(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    Date getExpiration(String token);
    boolean validateToken(String token, UserDetails userDetails);
    boolean isExpired(String token);
    SecretKey getSecretKey();
    String generateToken(PortalUser user);
}
