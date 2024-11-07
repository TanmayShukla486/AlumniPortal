package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "like_tbl",
        uniqueConstraints = {
        @UniqueConstraint(name = "unique_user_entity_constraint",
                columnNames = {"liked_by_id", "entity_type", "entity_id"})
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
    @Enumerated(EnumType.STRING)
    @Column(name = "entity_type")
    private LikeEntity type;
    @Column(name = "entity_id")
    private Long entityId;

}
