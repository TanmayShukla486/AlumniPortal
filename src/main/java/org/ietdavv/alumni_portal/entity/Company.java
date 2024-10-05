package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "company_details")
@Getter
@Setter
@ToString
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;
    @Column(name = "company_name", nullable = false)
    private String name;
    @Column(name = "years_spent", nullable = false)
    private int timeSpent;
    @Column(name = "currently_working", nullable = false, columnDefinition = "boolean default false")
    private boolean currentlyWorking;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private AlumniDetails alumni;

}
