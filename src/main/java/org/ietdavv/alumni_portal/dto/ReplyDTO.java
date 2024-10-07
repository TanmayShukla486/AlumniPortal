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
    private long commentId;

    public static ReplyDTO mapToDTO(Reply reply) {
        return ReplyDTO.builder()
                .username(reply.getReplier().getUsername())
                .content(reply.getContent())
                .likes((reply.getLikes() != null) ? reply.getLikes().size() : 0)
                .commentId(reply.getComment().getId())
                .build();
    }

}
