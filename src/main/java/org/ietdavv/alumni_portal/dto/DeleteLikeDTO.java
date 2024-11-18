package org.ietdavv.alumni_portal.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class DeleteLikeDTO {
    private Long id;
    private Long entityId;
}
