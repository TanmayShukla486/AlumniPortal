package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Like;
import org.ietdavv.alumni_portal.entity.LikeEntity;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {


}
