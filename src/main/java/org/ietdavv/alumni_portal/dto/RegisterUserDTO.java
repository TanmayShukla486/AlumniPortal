package org.ietdavv.alumni_portal.dto;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RegisterUserDTO {

    private String email;
    private String password;
    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String bio;
    private String role;
    private int graduationYear;
    private List<CompanyDTO> companies;
//    private List<AchievementDTO> achievements;

}
