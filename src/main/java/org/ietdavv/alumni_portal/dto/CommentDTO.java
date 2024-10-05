package org.ietdavv.alumni_portal.dto;


import lombok.*;
import org.ietdavv.alumni_portal.entity.Comment;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentDTO {

    private String username;
    private String content;
    private List<ReplyDTO> replies;
    private int likes;

    public static CommentDTO mapToDTO(Comment comment) {
        return CommentDTO.builder()
                .content(comment.getContent())
                .replies(comment.getReplies().stream().map(ReplyDTO::mapToDTO).toList())
                .likes(comment.getLikes().size())
                .username(comment.getCommenter().getUsername())
                .build();
    }

}
