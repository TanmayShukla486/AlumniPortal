package org.ietdavv.alumni_portal.controller;


import org.ietdavv.alumni_portal.dto.FollowDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/{username}/followers")
    public ResponseEntity<ResponseDTO<List<FollowDTO>>> getFollowers(
            @PathVariable String username) {
        return followService.getFollowers(username);
    }

    @GetMapping("/{username}/following")
    public ResponseEntity<ResponseDTO<List<FollowDTO>>> getFollowing(
            @PathVariable String username) {
        return followService.getFollowing(username);
    }

    @PostMapping("/{username}/follow")
    public ResponseEntity<FollowDTO> followUser(@PathVariable String username) {
        return followService.followUser(username);
    }

    @DeleteMapping("/{username}/unfollow")
    public ResponseEntity<Long> unfollowUser(@PathVariable String username) {
        return followService.unFollowUser(username);
    }

}
