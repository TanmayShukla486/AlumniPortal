package org.ietdavv.alumni_portal.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ImportantInfoDTO {
    private List<CompactInfoDTO> events;
    private List<CompactInfoDTO> jobs;
}
