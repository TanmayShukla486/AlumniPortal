package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}
