package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.LikeDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.Like;
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

import java.util.Optional;

@Service
public class LikeService implements LikeServiceInterface {
    private final LikeRepository    likeRepository;
    private final UserRepository    userRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<Long>> addLike(LikeDTO dto) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Like like = Like.builder()
                .likedBy(user)
                .type(dto.getEntityType())
                .entityId(dto.getEntityId())
                .build();
        likeRepository.save(like);
        Long likeCount = likeRepository.countByEntityId(dto.getEntityId());
        if (likeCount == null) likeCount = 0L;
        return ResponseEntity.ok(
                ResponseDTO.<Long>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(likeCount)
                        .build()
        );
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<Long>> removeLike(LikeDTO dto) {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Optional.ofNullable(likeRepository.findByEntityIdAndLikedBy(dto.getEntityId(), user))
                .ifPresent(
                        value -> value.ifPresent(likeRepository::delete)
                );
        Long likeCount = likeRepository.countByEntityId(dto.getEntityId());
        if (likeCount == null) likeCount = 0L;
        return ResponseEntity.ok(
                ResponseDTO.<Long>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(likeCount)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<Long>> getLikes(LikeDTO dto) {
        Long likeCount = likeRepository.countByEntityId(dto.getEntityId());
        return ResponseEntity.ok(
                ResponseDTO.<Long>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(likeCount == null ? 0L : likeCount)
                        .build()
        );
    }
}


