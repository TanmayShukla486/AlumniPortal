package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.CommentDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.Blog;
import org.ietdavv.alumni_portal.entity.Comment;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.entity.Role;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.error_handling.errors.UnAuthorizedCommandException;
import org.ietdavv.alumni_portal.repository.BlogRepository;
import org.ietdavv.alumni_portal.repository.CommentRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.CommentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements CommentServiceInterface {

    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(BlogRepository blogRepository,
                          CommentRepository commentRepository,
                          UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> getCommentsOnBlog(long blogId) {
        Blog blog = blogRepository
                .findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND));
        return ResponseEntity.ok(
                ResponseDTO.<List<CommentDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(blog.getComments().stream().map(CommentDTO::mapToDTO).toList())
                        .build()
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<CommentDTO>> addComment(CommentDTO dto) {

        PortalUser user = userRepository
                .findByUsername(dto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Blog blog = blogRepository
                .findById(dto.getBlogId())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND));

        Comment comment = Comment.builder()
                .commenter(user)
                .content(dto.getContent())
                .blog(blog)
                .build();
        CommentDTO saved = CommentDTO.mapToDTO(commentRepository.save(comment));
        return ResponseEntity.ok(
                ResponseDTO.<CommentDTO>builder()
                        .statusCode(201)
                        .message(ResponseMessage.COMMENT_ADDED)
                        .data(saved)
                        .build()
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<String>> deleteComment(long commentId) {
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.COMMENT_NOT_FOUND));
        PortalUser user = userRepository
                .findByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName()
                )
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        if (comment.getCommenter().equals(user) || user.getRole().equals(Role.ROLE_ALUMNI))
            commentRepository.delete(comment);
        else throw new UnAuthorizedCommandException(ResponseMessage.UNAUTHORIZED);
        return ResponseEntity.ok(
                ResponseDTO.<String>builder()
                        .statusCode(204)
                        .message(ResponseMessage.SUCCESS)
                        .data(ResponseMessage.COMMENT_DELETED)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> getCommentByBlogAndUser(long blogId, String username) {
        Blog blog = blogRepository
                .findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND));
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        List<CommentDTO> comments = commentRepository
                .findByBlogAndCommenter(blog, user)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.COMMENT_NOT_FOUND))
                .stream()
                .map(CommentDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<CommentDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(comments)
                        .build()
        );
    }
}
