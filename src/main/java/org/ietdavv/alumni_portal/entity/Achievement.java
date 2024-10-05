package org.ietdavv.alumni_portal.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "achievement")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "achievement_id")
    private Long id;
    @Column(name = "achievement_title", nullable = false)
    private String achievementTitle;
    @Column(name = "achievement_content", nullable = false, columnDefinition = "text")
    private String achievementContent;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private PortalUser user;

}
