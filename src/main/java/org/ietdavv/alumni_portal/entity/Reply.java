package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "reply_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;
    @Column(name = "reply_content", nullable = false, columnDefinition = "text")
    private String content;
    @ManyToOne
    @JoinColumn(name = "replier_id")
    private PortalUser replier;
    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
    @OneToMany(mappedBy = "entityId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes;

}
