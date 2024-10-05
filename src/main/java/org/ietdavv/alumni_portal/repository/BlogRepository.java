package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {
}
