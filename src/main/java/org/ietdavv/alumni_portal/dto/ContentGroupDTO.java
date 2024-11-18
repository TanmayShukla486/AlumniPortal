package org.ietdavv.alumni_portal.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ContentGroupDTO {

    private List<CompactContentDTO> blogs;
    private List<CompactContentDTO> comments;
}
