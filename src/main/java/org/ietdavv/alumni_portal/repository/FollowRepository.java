package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Follow;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findByFollower(PortalUser follower);
    Page<Follow> findByFollowing(PortalUser following, Pageable pageable);
    Optional<Follow> findByFollowerAndFollowing(PortalUser follower, PortalUser following);
    Long countByFollowing(PortalUser following);
    Long countByFollower(PortalUser following);

}
