package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Event;
import org.ietdavv.alumni_portal.entity.JobPosting;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CompactInfoDTO {

    private long id;
    private String title;
    private String info;

    public static CompactInfoDTO mapToDTO(Event event) {
        return CompactInfoDTO.builder()
                .id(event.getId())
                .title(event.getEventTitle())
                .info(event.getDate())
                .build();
    }

    public static CompactInfoDTO mapToDTO(JobPosting event) {
        return CompactInfoDTO.builder()
                .id(event.getId())
                .title(event.getJobTitle())
                .info(event.getStatus().name())
                .build();
    }

}
