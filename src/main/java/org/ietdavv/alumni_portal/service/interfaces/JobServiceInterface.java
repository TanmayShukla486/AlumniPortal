package org.ietdavv.alumni_portal.service.interfaces;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.JobPostingDTO;
import org.ietdavv.alumni_portal.entity.PostingStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface JobServiceInterface {
    ResponseEntity<List<JobPostingDTO>> getJobPostings();

    ResponseEntity<List<JobPostingDTO>> getJobPostingByUsername(String username);

    ResponseEntity<List<JobPostingDTO>> getJobPostingByLocation(String location);

    ResponseEntity<List<JobPostingDTO>> getJobPostingByTitle(String title);

    ResponseEntity<List<JobPostingDTO>> getJobPostingByRequirement(String req);

    ResponseEntity<String> addJobPostingRequest(JobPostingDTO dto);

    @Transactional
    ResponseEntity<String> approveJobPosting(Long id);

    @Transactional
    ResponseEntity<String> rejectJobPosting(Long id);

    @Transactional
    ResponseEntity<String> deleteJobPosting(Long id);

    ResponseEntity<JobPostingDTO> getJobPostingById(Long id);

    ResponseEntity<List<JobPostingDTO>> getJobPostingsByStatus(PostingStatus postingStatus);
}
