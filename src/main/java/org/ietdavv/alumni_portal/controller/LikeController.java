package org.ietdavv.alumni_portal.controller;

import org.hibernate.type.EntityType;
import org.ietdavv.alumni_portal.dto.LikeDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.LikeEntity;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.InvalidEntityException;
import org.ietdavv.alumni_portal.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    private void setEntity(LikeDTO dto) {
        String entity = dto.getEntity();
        if (entity.contains("blog")) dto.setEntityType(LikeEntity.BLOG);
        else if (entity.contains("comment")) dto.setEntityType(LikeEntity.COMMENT);
        else if (entity.contains("reply")) dto.setEntityType(LikeEntity.REPLY);
        else throw new InvalidEntityException(ResponseMessage.INVALID_ENTITY);
    }

    @GetMapping("/likes")
    public ResponseEntity<ResponseDTO<Long>> getLikes(@RequestBody LikeDTO dto) {
        setEntity(dto);
        return likeService.getLikes(dto);
    }

    @PostMapping("/likes")
    public ResponseEntity<ResponseDTO<Long>> addLike(@RequestBody LikeDTO dto) {
        setEntity(dto);
        return likeService.addLike(dto);
    }

    @DeleteMapping("/likes")
    public ResponseEntity<ResponseDTO<Long>> deleteLike(@RequestBody LikeDTO dto) {
        setEntity(dto);
        return likeService.removeLike(dto);
    }
}
