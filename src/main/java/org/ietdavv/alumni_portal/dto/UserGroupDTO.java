package org.ietdavv.alumni_portal.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UserGroupDTO {

    private List<CompactUserDTO> students;
    private List<CompactUserDTO> alumni;

}
