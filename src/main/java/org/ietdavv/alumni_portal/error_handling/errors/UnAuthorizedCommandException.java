package org.ietdavv.alumni_portal.error_handling.errors;

public class UnAuthorizedCommandException extends RuntimeException{
    public UnAuthorizedCommandException(String message) {
        super(message);
    }
}
