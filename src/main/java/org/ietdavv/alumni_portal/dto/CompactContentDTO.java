package org.ietdavv.alumni_portal.dto;


import lombok.*;
import org.ietdavv.alumni_portal.entity.Blog;
import org.ietdavv.alumni_portal.entity.Comment;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@ToString
@Builder
public class CompactContentDTO {

    private long id;
    private String username;
    private String title;
    private String content;
    private String createdAt;

    public static CompactContentDTO mapToDTO(Blog blog) {
        return CompactContentDTO.builder()
                .id(blog.getId())
                .username(blog.getAuthor().getUsername())
                .title(blog.getTitle())
                .createdAt(blog.getCreatedAt().toString())
                .content(blog.getContent()).build();
    }

    public static CompactContentDTO mapToDTO(Comment blog) {
        return CompactContentDTO.builder()
                .id(blog.getBlog().getId())
                .username(blog.getCommenter().getUsername())
                .title("Comment")
                .createdAt(blog.getCreatedAt().toString())
                .content(blog.getContent()).build();
    }
}
