package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Blog;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class BlogDTO {

    private Long id;
    private String title;
    private String category;
    private String color;
    private String author;
    private String content;
    private int likes;
    private boolean commentsEnabled;
    private int commentCount;
    private Date createdAt;

    public static BlogDTO mapToDTO(Blog blog) {
        return BlogDTO.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .color(blog.getCategory().getColor())
                .author(blog.getAuthor().getUsername())
                .category(blog.getCategory().getCategory())
                .content(blog.getContent())
                .commentsEnabled(blog.isCommentsEnabled())
                .likes(blog.getLikes().size())
                .createdAt(blog.getCreatedAt())
                .commentCount(blog.getComments() != null ? blog.getComments().size() : 0)
                .build();
    }

}
