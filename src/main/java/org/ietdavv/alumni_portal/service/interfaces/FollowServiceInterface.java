package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.FollowDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FollowServiceInterface {

    ResponseEntity<ResponseDTO<List<FollowDTO>>> getFollowers(long userId);
    ResponseEntity<ResponseDTO<List<FollowDTO>>> getFollowing(long userId);
    ResponseEntity<ResponseDTO<String>> followUser(long toBeFollowed);
    ResponseEntity<ResponseDTO<String>> unFollowUser(long unfollowId);
    ResponseEntity<ResponseDTO<Long>> getFollowerCount(long userId);
    ResponseEntity<ResponseDTO<Long>> getFollowingCount(long userId);

}
