package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "like_tbl2",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_user_blog_constraint",
                        columnNames = {"liked_by_id", "blog_id"}),
                @UniqueConstraint(name = "unique_user_comment_constraint",
                        columnNames = {"liked_by_id", "comment_id"})
        })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Like {
//TODO LIKE CONDITION NOT MATCHED
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "liked_by_id")
    private PortalUser likedBy;
    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
