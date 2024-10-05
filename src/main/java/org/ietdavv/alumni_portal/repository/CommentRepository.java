package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
