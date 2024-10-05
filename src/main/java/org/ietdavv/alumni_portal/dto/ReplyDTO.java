package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Reply;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDTO {

    private String username;
    private String content;
    private int likes;

    public static ReplyDTO mapToDTO(Reply reply) {
        return ReplyDTO.builder()
                .username(reply.getReplier().getUsername())
                .content(reply.getContent())
                .likes(reply.getLikes().size())
                .build();
    }

}
