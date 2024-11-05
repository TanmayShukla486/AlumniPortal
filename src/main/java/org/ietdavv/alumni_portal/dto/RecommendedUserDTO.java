package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.PortalUser;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendedUserDTO {

    private String username;
    private int graduationYear;

    public static RecommendedUserDTO mapToDTO(PortalUser user) {
        return RecommendedUserDTO.builder()
                .username(user.getUsername())
                .graduationYear(user.getGraduationYear())
                .build();
    }

}
