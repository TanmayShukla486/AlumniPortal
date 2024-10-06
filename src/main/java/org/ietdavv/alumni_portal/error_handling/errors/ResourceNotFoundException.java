package org.ietdavv.alumni_portal.error_handling.errors;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
