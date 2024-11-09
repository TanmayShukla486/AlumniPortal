package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.JobPostingDTO;
import org.ietdavv.alumni_portal.entity.JobPosting;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.entity.PostingStatus;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.JobPostingRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.JobServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class JobService implements JobServiceInterface {

    private final UserRepository userRepository;
    private final JobPostingRepository jobRepository;

    @Override
    public ResponseEntity<List<JobPostingDTO>> getJobPostings() {
        return ResponseEntity.ok(jobRepository.findAllByOrderByCreatedAt()
                .stream()
                .map(JobPostingDTO::mapToDTO)
                .toList());
    }

    @Override
    public ResponseEntity<List<JobPostingDTO>> getJobPostingByUsername(String username) {
        PortalUser alumni = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        return ResponseEntity.ok(jobRepository.findByAlumniOrderByCreatedAt(alumni)
                .stream()
                .map(JobPostingDTO::mapToDTO)
                .toList()
        );
    }
    @Override
    public ResponseEntity<List<JobPostingDTO>> getJobPostingByLocation(String location) {
        return ResponseEntity.ok(jobRepository.findByLocationContaining(location)
                .stream()
                .map(JobPostingDTO::mapToDTO)
                .toList()
        );
    }

    @Override
    public ResponseEntity<List<JobPostingDTO>> getJobPostingByTitle(String title) {
        return ResponseEntity.ok(jobRepository.findByJobTitleContaining(title)
                .stream()
                .map(JobPostingDTO::mapToDTO)
                .toList()
        );
    }
    @Override
    public ResponseEntity<List<JobPostingDTO>> getJobPostingByRequirement(String req) {
        return ResponseEntity.ok(jobRepository.findByJobRequirementsContaining(req)
                .stream()
                .map(JobPostingDTO::mapToDTO)
                .toList()
        );
    }

    @Override
    @Transactional
    public ResponseEntity<String> addJobPostingRequest(JobPostingDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PortalUser alumni = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        JobPosting jobPosting = JobPosting.builder()
                .jobTitle(dto.getTitle())
                .jobRequirements(dto.getReq())
                .jobDescription(dto.getReq())
                .link(dto.getLink())
                .salary(dto.getSal())
                .status(PostingStatus.PENDING)
                .location(dto.getLoc())
                .alumni(alumni)
                .build();
        jobRepository.save(jobPosting);
        return ResponseEntity.ok(ResponseMessage.SUCCESS);
    }

    @Transactional
    @Override
    public ResponseEntity<String> approveJobPosting(Long id) {
        JobPosting jobPosting = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.JOB_NOT_FOUND));
        jobPosting.setStatus(PostingStatus.APPROVED);
        jobRepository.save(jobPosting);
        return ResponseEntity.ok(ResponseMessage.SUCCESS);
    }

    @Transactional
    @Override
    public ResponseEntity<String> rejectJobPosting(Long id) {
        JobPosting jobPosting = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.JOB_NOT_FOUND));
        jobPosting.setStatus(PostingStatus.REJECTED);
        jobRepository.save(jobPosting);
        return ResponseEntity.ok(ResponseMessage.SUCCESS);
    }

    @Transactional
    @Override
    public ResponseEntity<String> deleteJobPosting(Long id) {
        JobPosting jobPosting = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.JOB_NOT_FOUND));
        jobRepository.delete(jobPosting);
        return ResponseEntity.ok(ResponseMessage.SUCCESS);
    }



}
