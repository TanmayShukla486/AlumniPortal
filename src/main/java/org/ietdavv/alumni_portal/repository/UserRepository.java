package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<PortalUser, Long> {

    Optional<PortalUser> findByUsername(String username);

}
