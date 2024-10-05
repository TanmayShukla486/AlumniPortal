package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "like_tbl")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "liked_by_id")
    private PortalUser likedBy;
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private LikeEntity type;
    @Column(name = "entity_id")
    private Long entityId;

}
