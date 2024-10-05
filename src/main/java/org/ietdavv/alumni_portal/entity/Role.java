package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

public enum Role {
    ROLE_ADMIN,
    ROLE_STUDENT,
    ROLE_ALUMNI
}
