package org.ietdavv.alumni_portal.dto;

import jakarta.transaction.Transactional;
import lombok.*;
import org.hibernate.type.EntityType;
import org.ietdavv.alumni_portal.entity.LikeEntity;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class LikeDTO {

    String entity;
    LikeEntity entityType;
    Long entityId;

}
