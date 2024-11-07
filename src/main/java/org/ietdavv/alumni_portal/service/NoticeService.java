package org.ietdavv.alumni_portal.service;

import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.NoticeDTO;
import org.ietdavv.alumni_portal.entity.Notice;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.NoticeRepository;
import org.ietdavv.alumni_portal.service.interfaces.NoticeServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoticeService implements NoticeServiceInterface {

    private final NoticeRepository noticeRepository;

    @Override
    public ResponseEntity<List<NoticeDTO>> getNotices() {
        return ResponseEntity.ok(noticeRepository.findAll()
                .stream()
                .map(NoticeDTO::mapToDTO)
                .toList());
    }

    @Override
    public ResponseEntity<String> addNotice(NoticeDTO dto) {
        Notice notice = Notice.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
        noticeRepository.save(notice);
        return ResponseEntity.ok(ResponseMessage.SUCCESS);
    }

    @Override
    public ResponseEntity<String> removeNotice(Long id) {
        Notice notice = noticeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.NOTICE_NOT_FOUND));
        noticeRepository.delete(notice);
        return ResponseEntity.ok(ResponseMessage.SUCCESS);
    }
}
