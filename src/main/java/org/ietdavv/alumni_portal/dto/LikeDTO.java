package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Like;
import org.ietdavv.alumni_portal.entity.LikeEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LikeDTO {

    private Long id;
    private String username;

    public static LikeDTO mapToDTO(Like like) {
        return LikeDTO.builder()
                .id(like.getId())
                .username(like.getLikedBy().getUsername())
                .build();
    }
}
