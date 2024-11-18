package org.ietdavv.alumni_portal.error_handling.errors;

public class InvalidValueException extends RuntimeException {
    public InvalidValueException(String message) {
        super(message);
    }
}
