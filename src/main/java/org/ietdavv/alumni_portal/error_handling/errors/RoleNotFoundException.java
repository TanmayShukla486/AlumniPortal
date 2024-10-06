package org.ietdavv.alumni_portal.error_handling.errors;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String message) {
        super(message);
    }

}
