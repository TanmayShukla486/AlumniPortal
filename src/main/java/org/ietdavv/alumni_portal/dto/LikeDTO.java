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
    private String entityType;
    private Long entityId;

    public static LikeEntity getEntity(String type) {
        if (type.equalsIgnoreCase("BLOG")) return LikeEntity.BLOG;
        else if (type.equalsIgnoreCase("COMMENT")) return LikeEntity.COMMENT;
        return LikeEntity.REPLY;
    }

    public static LikeDTO mapToDTO(Like like) {
        return LikeDTO.builder()
                .id(like.getId())
                .username(like.getLikedBy().getUsername())
                .entityId(like.getEntityId())
                .entityType(like.getType().name())
                .build();
    }
}
