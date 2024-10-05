package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
