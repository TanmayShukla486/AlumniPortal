package org.ietdavv.alumni_portal.error_handling;

import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.error_handling.errors.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleCredentialException(InvalidCredentials e) {
        return ResponseEntity.status(403).body(ResponseMessage.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidJwt(MalformedJwtException e) {
        return ResponseEntity.status(400).body("Jwt token is corrupted or invalid");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNullException(NullPointerException e) {
        return ResponseEntity.status(400).body("Invalid input data");
    }

    @ExceptionHandler
    public ResponseEntity<String> handleResourceException(ResourceNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleRoleException(RoleNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleUnauthorized(UnAuthorizedCommandException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidVal(InvalidValueException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleLoginException(BadCredentialsException e) {
        return ResponseEntity.status(401).body("Invalid Credentials for login");
    }
}
