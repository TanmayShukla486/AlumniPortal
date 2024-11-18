package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.FollowDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowServiceInterface {

    ResponseEntity<ResponseDTO<List<FollowDTO>>> getFollowers(String userId);
    ResponseEntity<ResponseDTO<List<FollowDTO>>> getFollowing(String userId);
    ResponseEntity<FollowDTO> followUser(String toBeFollowed);
    ResponseEntity<Long> unFollowUser(String unfollowId);
    ResponseEntity<ResponseDTO<Long>> getFollowerCount(String userId);
    ResponseEntity<ResponseDTO<Long>> getFollowingCount(String userId);

}
