package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.DeleteLikeDTO;
import org.ietdavv.alumni_portal.dto.LikeDTO;
import org.ietdavv.alumni_portal.entity.*;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.error_handling.errors.UnAuthorizedCommandException;
import org.ietdavv.alumni_portal.repository.BlogRepository;
import org.ietdavv.alumni_portal.repository.CommentRepository;
import org.ietdavv.alumni_portal.repository.LikeRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public ResponseEntity<LikeDTO> addLike(String type, Long id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PortalUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Blog blog = null;
        if (type.equalsIgnoreCase("BLOG"))
            blog = blogRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND));
        Comment comment = null;
        if (type.equalsIgnoreCase("COMMENT"))
            comment = commentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND));

        Like like = Like.builder()
                .likedBy(user)
                .blog(blog)
                .comment(comment)
                .build();
        Like saved = likeRepository.save(like);
        return ResponseEntity.ok(LikeDTO.mapToDTO(saved));
    }

    @Transactional
    public ResponseEntity<DeleteLikeDTO> deleteLike(Long id) {
        Like like = likeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.LIKE_NOT_FOUND));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!like.getLikedBy().getUsername().equals(username))
            throw new UnAuthorizedCommandException(ResponseMessage.UNAUTHORIZED);
        likeRepository.delete(like);
        Long entityId = like.getComment() == null ? like.getBlog().getId() : like.getComment().getId();
        return ResponseEntity.ok(DeleteLikeDTO.builder().id(like.getId()).entityId(entityId).build());
    }

    public ResponseEntity<List<LikeDTO>> getLikes(String type, Long id) {
        if (type.equalsIgnoreCase("blog")) {
            Blog blog = blogRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND));
            return ResponseEntity.ok(blog.getLikes().stream().map(LikeDTO::mapToDTO).toList());
        }
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.COMMENT_NOT_FOUND));
        return ResponseEntity.ok(comment.getLikes().stream().map(LikeDTO::mapToDTO).toList());
    }
}


