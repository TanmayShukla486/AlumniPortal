package org.ietdavv.alumni_portal.dto;


import lombok.*;
import org.ietdavv.alumni_portal.entity.Comment;
import org.ietdavv.alumni_portal.entity.Like;

import java.util.Arrays;
import java.util.List;

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
    private List<LikeDTO> likes;

    public static CommentDTO mapToDTO(Comment comment) {
        List<Like> commentLikes = comment.getLikes();
        List<LikeDTO> likes = commentLikes == null ? List.of() : commentLikes.stream().map(LikeDTO::mapToDTO).toList();
        return CommentDTO.builder()
                .blogId(comment.getBlog().getId())
                .id(comment.getId())
                .content(comment.getContent())
                .likes(likes)
                .username(comment.getCommenter().getUsername())
                .build();
    }

}
