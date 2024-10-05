package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Achievement;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AchievementDTO {

    private String title;
    private String content;

    public static AchievementDTO mapToDTO(Achievement achievement) {
        return AchievementDTO.builder()
                .title(achievement.getAchievementTitle())
                .content(achievement.getAchievementContent())
                .build();
    }

}
