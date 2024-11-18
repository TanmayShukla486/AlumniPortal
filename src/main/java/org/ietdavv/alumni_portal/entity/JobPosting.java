package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "job_posting_tbl")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPosting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "job_title", nullable = false)
    private String jobTitle;
    @Column(name = "job_requirements", columnDefinition = "text", nullable = false)
    private String jobRequirements;
    @Column(name = "job_description", columnDefinition = "text", nullable = false)
    private String jobDescription;
    @Column(name = "job_location", nullable = false)
    private String location;
    @Column(name = "salary_in_lpa")
    private int salary;
    @Column(name = "apply_link", nullable = false)
    private String link;
    @ManyToOne
    @JoinColumn(name = "posted_by_id")
    private PortalUser alumni;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "posting_status")
    private PostingStatus status;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamptz default now()")
    private Timestamp createdAt;
}
