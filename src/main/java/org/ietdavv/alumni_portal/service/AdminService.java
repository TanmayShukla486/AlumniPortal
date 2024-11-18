package org.ietdavv.alumni_portal.service;

import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.CompactContentDTO;
import org.ietdavv.alumni_portal.dto.CompactInfoDTO;
import org.ietdavv.alumni_portal.dto.CompactUserDTO;
import org.ietdavv.alumni_portal.dto.StatsDTO;
import org.ietdavv.alumni_portal.entity.PostingStatus;
import org.ietdavv.alumni_portal.entity.Role;
import org.ietdavv.alumni_portal.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final JobPostingRepository jobPostingRepository;

    public List<CompactContentDTO> getBlogs() {
        return blogRepository.findAll()
                .stream()
                .map(CompactContentDTO::mapToDTO)
                .toList();
    }

    public List<CompactContentDTO> getComments() {
        return commentRepository.findAll()
                .stream()
                .map(CompactContentDTO::mapToDTO)
                .toList();
    }

    public List<CompactUserDTO> getStudents() {
        return
                userRepository.findByRole(Role.ROLE_STUDENT)
                        .stream()
                        .map(CompactUserDTO::mapToDTO)
                        .toList();
    }

    public List<CompactUserDTO> getAlumni() {
        return userRepository.findByRole(Role.ROLE_ALUMNI)
                .stream()
                .map(CompactUserDTO::mapToDTO)
                .toList();
    }

    public ResponseEntity<StatsDTO> getStats() {
        long userCount = userRepository.count();
        long commentCount = commentRepository.count();
        long blogCount = blogRepository.count();
        long categoryCount = categoryRepository.count();
        long alumniCount = userRepository.countByRole(Role.ROLE_ALUMNI);
        long studentCount = userRepository.countByRole(Role.ROLE_STUDENT);
        long postingsCount = jobPostingRepository.count();
        long acceptedPostings = jobPostingRepository.countByStatus(PostingStatus.APPROVED);
        long rejectedPostings = jobPostingRepository.countByStatus(PostingStatus.REJECTED);
        long pendingPostings = postingsCount - (acceptedPostings + rejectedPostings);
        long eventCount = eventRepository.count();
        return ResponseEntity.ok(StatsDTO.builder()
                        .numberOfUsers(userCount)
                        .numberOfComments(commentCount)
                        .numberOfBlogs(blogCount)
                        .numberOfCategories(categoryCount)
                        .numberOfStudents(studentCount)
                        .numberOfAlumni(alumniCount)
                        .numberOfPostings(postingsCount)
                        .acceptedPostings(acceptedPostings)
                        .rejectedPostings(rejectedPostings)
                        .pendingPostings(pendingPostings)
                        .numberOfEvents(eventCount)
                .build());
    }

    public List<CompactInfoDTO> getEvents() {
        return eventRepository.findAll()
                .stream()
                .map(CompactInfoDTO::mapToDTO)
                .toList();
    }

    public List<CompactInfoDTO> getJobs() {
        return jobPostingRepository.findAll()
                .stream()
                .map(CompactInfoDTO::mapToDTO)
                .toList();
    }
}
