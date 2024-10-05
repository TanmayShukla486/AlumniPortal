package org.ietdavv.alumni_portal.dto;

import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class LoginUserDTO {

    private String username;
    private String password;

}
