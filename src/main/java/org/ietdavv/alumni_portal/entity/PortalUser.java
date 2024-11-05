package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "portal_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PortalUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "password")
    private String password;
    @Column(name = "bio", columnDefinition = "text")
    private String bio;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(name = "department")
    private Department department;
    @Column(name = "graduation_year")
    private int graduationYear;
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Achievement> achievements;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AlumniDetails alumniDetails;
    @OneToMany(mappedBy = "follower")
    private Set<Follow> following;
    @OneToMany(mappedBy = "following")
    private Set<Follow> followers;
    @OneToMany(mappedBy = "author") // only possible if you are an author
    private List<Blog> blogs;
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, columnDefinition = "timestamptz default now()")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false, columnDefinition = "timestamptz default now()")
    private Timestamp updatedAt;
}
