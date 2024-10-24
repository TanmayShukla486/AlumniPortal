package org.ietdavv.alumni_portal.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LoginResponseDTO {

    private String username;
    private String token;
    private String refreshToken;
    private String role;

}
