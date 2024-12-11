package org.ietdavv.alumni_portal.controller;

import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.*;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.service.AdminService;
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
    private final AdminService adminService;

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

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<StatsDTO> getAllStats() {
        return adminService.getStats();
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserGroupDTO> getAllUsers() {
        List<CompactUserDTO> students = adminService.getStudents();
        List<CompactUserDTO> alumni = adminService.getAlumni();
        return ResponseEntity.ok(UserGroupDTO.builder()
                        .students(students)
                        .alumni(alumni)
                .build());
    }

    @GetMapping("/content")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ContentGroupDTO> getContent() {
        List<CompactContentDTO> blogs = adminService.getBlogs();
        List<CompactContentDTO> comments = adminService.getComments();
        return ResponseEntity.ok(
                ContentGroupDTO.builder()
                        .blogs(blogs)
                        .comments(comments)
                        .build()
        );
    }

    @GetMapping("/important")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ImportantInfoDTO> getImportant() {
        List<CompactInfoDTO> events = adminService.getEvents();
        List<CompactInfoDTO> jobs = adminService.getJobs();
        return ResponseEntity.ok(ImportantInfoDTO.builder().events(events).jobs(jobs).build());
    }

}
