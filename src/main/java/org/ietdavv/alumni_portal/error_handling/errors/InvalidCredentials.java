package org.ietdavv.alumni_portal.error_handling.errors;

public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials(String message) {
        super(message);
    }
}
