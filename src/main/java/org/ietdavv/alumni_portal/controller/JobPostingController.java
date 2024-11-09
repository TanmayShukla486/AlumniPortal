package org.ietdavv.alumni_portal.controller;

import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.JobPostingDTO;
import org.ietdavv.alumni_portal.service.JobService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class JobPostingController {

    private final JobService service;

    @GetMapping("/postings")
    public ResponseEntity<List<JobPostingDTO>> getPostings() {
        return service.getJobPostings();
    }

    @GetMapping("/posting?requirements={req}")
    public ResponseEntity<List<JobPostingDTO>> getByRequirements(@PathVariable(name = "req") String req) {
        return service.getJobPostingByRequirement(req);
    }

    @GetMapping("/posting?title={title}")
    public ResponseEntity<List<JobPostingDTO>> getByTitle(@PathVariable(name = "title") String title) {
        return service.getJobPostingByTitle(title);
    }

    @GetMapping("/posting?location={loc}")
    public ResponseEntity<List<JobPostingDTO>> getByLocation(@PathVariable(name = "loc") String loc) {
        return service.getJobPostingByLocation(loc);
    }

    @PostMapping("/postings")
    @PreAuthorize("hasRole('ROLE_ALUMNI')")
    public ResponseEntity<String> addPosting(@RequestBody JobPostingDTO dto) {
        return service.addJobPostingRequest(dto);
    }

    @PutMapping("/posting/{id}/{status}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updatePosting(@PathVariable(name = "id") Long id,
                                                @PathVariable(name = "status") String status) {
        if (status.equalsIgnoreCase("approve")) return service.approveJobPosting(id);
        else return service.rejectJobPosting(id);
    }
    @DeleteMapping("/posting/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deletePosting(@PathVariable(name = "id") Long id) {
        return service.deleteJobPosting(id);
    }
}
