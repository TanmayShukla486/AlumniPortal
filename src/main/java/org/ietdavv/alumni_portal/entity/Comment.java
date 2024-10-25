package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "comment_tbl")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    @Column(name = "content", nullable = false, columnDefinition = "text")
    private String content;
    @ManyToOne
    @JoinColumn(name = "commenter_id")
    private PortalUser commenter;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamptz default now()")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamptz default now()")
    private Timestamp updatedAt;
    @OneToMany(mappedBy = "entityId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

}
