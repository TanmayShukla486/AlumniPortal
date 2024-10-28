package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Blog;
import org.ietdavv.alumni_portal.entity.Category;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Optional<List<Blog>> findByCategory(Category category);

    Optional<List<Blog>> findByAuthor(PortalUser author);

    Optional<List<Blog>> findByAuthorAndCategory(PortalUser author, Category category);

    Optional<List<Blog>> findAllByOrderByCreatedAtDesc();

    @Query("SELECT b FROM Blog b LEFT JOIN b.likes l GROUP BY b ORDER BY COUNT(l) ASC")
    Optional<List<Blog>> findAllOrderByLikesAsc();
}
