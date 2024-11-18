package org.ietdavv.alumni_portal.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.entity.Follow;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Slf4j
@Builder
public class FollowDTO {

    private Long id;
    private String followedBy;
    private String followed;

    public static FollowDTO mapToDTO(Follow follow) {
        return FollowDTO.builder()
                .id(follow.getId())
                .followedBy(follow.getFollower().getUsername())
                .followed(follow.getFollowing().getUsername())
                .build();
    }

}
