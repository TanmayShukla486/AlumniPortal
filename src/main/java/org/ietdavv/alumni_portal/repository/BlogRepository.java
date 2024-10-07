package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Blog;
import org.ietdavv.alumni_portal.entity.Category;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<List<Blog>> findByCategory(Category category);

    Optional<List<Blog>> findByAuthor(PortalUser author);

}
