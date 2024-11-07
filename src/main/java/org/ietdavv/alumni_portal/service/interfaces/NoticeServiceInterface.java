package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.NoticeDTO;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NoticeServiceInterface {

    ResponseEntity<List<NoticeDTO>> getNotices();
    ResponseEntity<String> addNotice(NoticeDTO dto);
    ResponseEntity<String> removeNotice(Long id);
}
