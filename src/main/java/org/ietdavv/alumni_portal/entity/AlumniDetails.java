package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "alumni_details")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AlumniDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detail_id")
    private Long id;
    @Column(name = "graduation_year")
    private int graduationYear;
    @OneToMany(mappedBy = "alumni")
    private List<Company> companies;
    @OneToOne(cascade = CascadeType.ALL)
    private PortalUser user;

}
