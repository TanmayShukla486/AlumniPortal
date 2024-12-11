package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.JobPosting;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class JobPostingDTO {

    private Long id;
    private String title;
    private String req;
    private String desc;
    private String loc;
    private int sal;
    private String link;
    private String postedBy;
    private String status;
    private String company;

    public static JobPostingDTO mapToDTO(JobPosting jobPosting) {
        return JobPostingDTO.builder()
                .id(jobPosting.getId())
                .title(jobPosting.getJobTitle())
                .req(jobPosting.getJobRequirements())
                .desc(jobPosting.getJobDescription())
                .link(jobPosting.getLink())
                .company(jobPosting.getCompany())
                .loc(jobPosting.getLocation())
                .sal(jobPosting.getSalary())
                .status(jobPosting.getStatus().name())
                .postedBy(jobPosting.getAlumni().getUsername())
                .build();
    }

}
