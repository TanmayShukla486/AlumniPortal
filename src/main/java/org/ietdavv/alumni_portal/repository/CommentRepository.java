package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Blog;
import org.ietdavv.alumni_portal.entity.Comment;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<List<Comment>> findByBlogAndCommenter(Blog blog, PortalUser commenter);

}
