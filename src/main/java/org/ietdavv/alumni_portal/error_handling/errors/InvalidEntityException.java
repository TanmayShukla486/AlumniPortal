package org.ietdavv.alumni_portal.error_handling.errors;

public class InvalidEntityException extends RuntimeException {
    public InvalidEntityException(String message) {
        super(message);
    }
}
