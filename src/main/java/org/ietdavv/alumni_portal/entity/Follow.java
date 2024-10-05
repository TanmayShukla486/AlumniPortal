package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "follow_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private PortalUser follower;
    @ManyToOne
    @JoinColumn(name = "following_id")
    private PortalUser following;


}
