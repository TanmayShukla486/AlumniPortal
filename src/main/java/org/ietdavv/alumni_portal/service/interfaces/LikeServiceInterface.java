package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.LikeDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface LikeServiceInterface {

    ResponseEntity<ResponseDTO<Long>> addLike(LikeDTO dto);
    ResponseEntity<ResponseDTO<Long>> removeLike(LikeDTO dto);
    ResponseEntity<ResponseDTO<Long>> getLikes(LikeDTO dto);

}
