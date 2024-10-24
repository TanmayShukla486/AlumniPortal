package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.PortalUser;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CompactUserDTO {

    private String username;
    private String email;
    private String role;

    public static CompactUserDTO mapToDTO(PortalUser user) {
        String role = user.getRole().name().substring(5);
        return CompactUserDTO.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .role(role.charAt(0) + role.substring(1))
                .build();
    }

}
