package org.ietdavv.alumni_portal.dto;


import lombok.*;
import org.ietdavv.alumni_portal.entity.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CommentDTO {

    private long blogId;
    private long id ;
    private String username;
    private String content;
    private int likes;

    public static CommentDTO mapToDTO(Comment comment) {
        return CommentDTO.builder()
                .blogId(comment.getBlog().getId())
                .id(comment.getId())
                .content(comment.getContent())
                .likes(comment.getLikes().size())
                .username(comment.getCommenter().getUsername())
                .build();
    }

}
