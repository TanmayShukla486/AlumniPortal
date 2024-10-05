package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.CommentDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentServiceInterface {

    ResponseEntity<ResponseDTO<List<CommentDTO>>> getCommentsOnBlog(long blogId);
    ResponseEntity<ResponseDTO<CommentDTO>> addComment(long blogId);
    ResponseEntity<ResponseDTO<String>> deleteComment(long commentId);
    ResponseEntity<ResponseDTO<List<CommentDTO>>> getCommentByBlogAndUser(long blogId, long userId);

}
