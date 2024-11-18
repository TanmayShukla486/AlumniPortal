package org.ietdavv.alumni_portal.controller;

import org.ietdavv.alumni_portal.dto.LikeDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
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
        return likeService.getLikes(LikeDTO.getEntity(type), id);
    }

    @PostMapping("/likes")
    public ResponseEntity<LikeDTO> addLike(@RequestBody LikeDTO dto) {
       return likeService.addLike(dto);
    }

    @DeleteMapping("/likes")
    public ResponseEntity<Long> deleteLike(@RequestParam(name = "id") Long id) {
        return likeService.removeLike(id);
    }
}
