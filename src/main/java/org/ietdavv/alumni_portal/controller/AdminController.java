package org.ietdavv.alumni_portal.controller;

import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.EventDTO;
import org.ietdavv.alumni_portal.dto.NoticeDTO;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.service.EventService;
import org.ietdavv.alumni_portal.service.NoticeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final EventService eventService;
    private final NoticeService noticeService;

    // Event Handling
    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getEvents() {
        return eventService.getEvents();
    }

    @PostMapping("/events")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addEvent(@RequestBody EventDTO eventDTO){
        return eventService.createEvent(eventDTO);
    }

    @DeleteMapping("/events/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteEvent(@PathVariable(name = "id") Long id) {
        return eventService.deleteEvent(id);
    }

    //Notice Handling

    @GetMapping("/notices")
    public ResponseEntity<List<NoticeDTO>> getNotices() {
        return noticeService.getNotices();
    }

    @PostMapping("/notices")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> addNotice(NoticeDTO dto) {
        return noticeService.addNotice(dto);
    }

    @DeleteMapping("/notices/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteNotice(@PathVariable(name = "id") Long id) {
        return noticeService.removeNotice(id);
    }
}
