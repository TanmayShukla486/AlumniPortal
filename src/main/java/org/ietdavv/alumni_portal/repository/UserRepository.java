package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<PortalUser, Long> {

    Optional<PortalUser> findByUsername(String username);

    List<PortalUser> findByRole(Role role);

    List<PortalUser> findByRoleAndGraduationYear(Role role, Integer year);

}
