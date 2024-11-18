package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.JobPosting;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.entity.PostingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {

    List<JobPosting> findAllByOrderByCreatedAt();
    List<JobPosting> findByAlumniOrderByCreatedAt(PortalUser alumni);
    List<JobPosting> findByLocationContaining(String location);
    List<JobPosting> findByJobTitleContaining(String title);
    List<JobPosting> findByJobRequirementsContaining(String req);

    List<JobPosting> findByStatus(PostingStatus postingStatus);
}
