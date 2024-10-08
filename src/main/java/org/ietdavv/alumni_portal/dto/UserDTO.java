package org.ietdavv.alumni_portal.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.entity.AlumniDetails;
import org.ietdavv.alumni_portal.entity.PortalUser;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
@Builder
public class UserDTO {

    private String username;
    private String firstName;
    private String lastName;
    private String middleName;
    private String role;
    private int graduationYear;
    private boolean isAlumni;
    private AlumniDetailsDTO details;
    private List<FollowDTO> followers;
    private List<AchievementDTO> achievements;
    private List<FollowDTO> following;

    public static UserDTO mapToDTO(PortalUser user) {
        String role = user.getRole().name().substring(5);
        List<FollowDTO> followers = user.getFollowers().stream().map(FollowDTO::mapToDTO).toList();
        List<FollowDTO> following = user.getFollowing().stream().map(FollowDTO::mapToDTO).toList();
        AlumniDetails deets = user.getAlumniDetails();
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .role(role.charAt(0) + role.substring(1).toLowerCase())
                .graduationYear(user.getGraduationYear())
                .isAlumni(role.equals("ALUMNI"))
                .followers(followers)
                .username(user.getUsername())
                .following(following)
                .details(deets != null ? AlumniDetailsDTO.mapToDTO(deets) : null)
                .achievements(user.getAchievements().stream().map(AchievementDTO::mapToDTO).toList())
                .build();
    }

}
