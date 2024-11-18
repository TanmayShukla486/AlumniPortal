package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.LikeDTO;
import org.ietdavv.alumni_portal.entity.Like;
import org.ietdavv.alumni_portal.entity.LikeEntity;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.LikeRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.LikeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService implements LikeServiceInterface {
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<LikeDTO> addLike(LikeDTO dto) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Like like = Like.builder()
                .likedBy(user)
                .type(LikeDTO.getEntity(dto.getEntityType()))
                .entityId(dto.getEntityId())
                .build();
        Like saved = likeRepository.save(like);
        return ResponseEntity.ok(
                LikeDTO.mapToDTO(saved)
        );
    }

    @Transactional
    @Override
    public ResponseEntity<Long> removeLike(Long id) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Like like = likeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.LIKE_NOT_FOUND));
        likeRepository.delete(like);
        return ResponseEntity.ok(like.getId());
    }

    @Override
    public ResponseEntity<List<LikeDTO>> getLikes(LikeEntity type, Long eId) {
        List<Like> likes = likeRepository.findByEntityTypeAndEntityId(type, eId);
        return ResponseEntity.ok(
            likes.stream().map(LikeDTO::mapToDTO).toList()
        );
    }
}


