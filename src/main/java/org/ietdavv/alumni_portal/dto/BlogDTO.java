package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Blog;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BlogDTO {

    private String title;
    private String category;
    private String author;
    private String content;
    private List<CommentDTO> comments;
    private int likes;
    private boolean commentsEnabled;

    public static BlogDTO mapToDTO(Blog blog) {
        return BlogDTO.builder()
                .title(blog.getTitle())
                .author(blog.getAuthor().getUsername())
                .content(blog.getContent())
                .commentsEnabled(blog.isCommentsEnabled())
                .comments(blog.getComments().stream().map(CommentDTO::mapToDTO).toList())
                .likes(blog.getLikes().size())
                .build();
    }

}
