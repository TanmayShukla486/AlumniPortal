package org.ietdavv.alumni_portal.controller;

import org.ietdavv.alumni_portal.dto.DeleteLikeDTO;
import org.ietdavv.alumni_portal.dto.LikeDTO;
import org.ietdavv.alumni_portal.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/likes/{type}")
    public ResponseEntity<List<LikeDTO>> getLikes(@PathVariable(name = "type") String type,
                                                  @RequestParam(name = "id") Long id) {
        return likeService.getLikes(type, id);
    }

    @PostMapping("/likes/{type}/{id}")
    public ResponseEntity<LikeDTO> addLike(
            @PathVariable(name = "type") String type,
            @PathVariable(name = "id") Long id) {
       return likeService.addLike(type, id);
    }

    @DeleteMapping("/likes")
    public ResponseEntity<DeleteLikeDTO> deleteLike(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "type") String type) {
        return likeService.deleteLike(id);
    }
}
