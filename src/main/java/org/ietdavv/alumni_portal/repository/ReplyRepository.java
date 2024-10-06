package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Comment;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository extends JpaRepository<Reply, Long>{

    Optional<Reply> findByComment(Comment comment);
    Long countByComment(Comment comment);
    Optional<List<Reply>> findByCommentAndReplier(Comment comment, PortalUser repier);
}
