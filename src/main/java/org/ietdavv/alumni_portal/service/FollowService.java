package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.FollowDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.Follow;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.FollowRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.FollowServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class FollowService implements FollowServiceInterface {

    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    @Autowired
    public FollowService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }


    @Override
    public ResponseEntity<ResponseDTO<List<FollowDTO>>> getFollowers(String username) {
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Pageable sortedByMostRecent = PageRequest
                .of(0, 10, Sort.by("createdAt"));
        List<FollowDTO> data = followRepository.findByFollowing(user, sortedByMostRecent)
                .stream()
                .map(FollowDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<FollowDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(data)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<List<FollowDTO>>> getFollowing(String username) {
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        List<FollowDTO> data = followRepository.findByFollower(user)
                .stream()
                .map(FollowDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<FollowDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(data)
                        .build()
        );
    }

    @Override
    @Transactional
    public ResponseEntity<FollowDTO> followUser(String toBeFollowed) {
        PortalUser following = userRepository
                .findByUsername(toBeFollowed)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PortalUser follower = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Follow follow = Follow.builder()
                .following(following)
                .follower(follower)
                .build();
        Follow saved = followRepository.save(follow);
        return ResponseEntity.ok(FollowDTO.mapToDTO(saved));

    }

    @Override
    @Transactional
    public ResponseEntity<Long> unFollowUser(String f2) {
        String f1 = SecurityContextHolder.getContext().getAuthentication().getName();
        PortalUser follower = userRepository
                .findByUsername(f1)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        PortalUser following = userRepository
                .findByUsername(f2)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Follow follow = followRepository
                .findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.FOLLOW_NOT_FOUND));
        followRepository.delete(follow);
        return ResponseEntity.ok(follow.getId());
    }

    @Override
    public ResponseEntity<ResponseDTO<Long>> getFollowerCount(String username) {
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Long count = followRepository.countByFollowing(user);
        return ResponseEntity.ok(ResponseDTO.<Long>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(count)
                .build());
    }

    @Override
    public ResponseEntity<ResponseDTO<Long>> getFollowingCount(String username) {
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Long count = followRepository.countByFollower(user);
        return ResponseEntity.ok(ResponseDTO.<Long>builder()
                .statusCode(200)
                .message(ResponseMessage.SUCCESS)
                .data(count)
                .build());
    }
}
