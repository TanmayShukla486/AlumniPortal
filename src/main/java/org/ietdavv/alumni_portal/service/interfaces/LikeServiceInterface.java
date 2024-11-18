package org.ietdavv.alumni_portal.service.interfaces;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.LikeDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.LikeEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LikeServiceInterface {

    ResponseEntity<LikeDTO> addLike(LikeDTO dto);

    @Transactional
    ResponseEntity<Long> removeLike(Long id, LikeEntity type);


    ResponseEntity<List<LikeDTO>> getLikes(LikeEntity type, Long eId);
}
