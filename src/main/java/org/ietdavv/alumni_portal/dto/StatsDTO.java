package org.ietdavv.alumni_portal.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@ToString
@Builder
public class StatsDTO {

    private long numberOfUsers;
    private long numberOfStudents;
    private long numberOfAlumni;
    private long numberOfBlogs;
    private long numberOfComments;
    private long numberOfCategories;
    private long numberOfEvents;
    private long numberOfPostings;
    private long rejectedPostings;
    private long acceptedPostings;
    private long pendingPostings;

}
