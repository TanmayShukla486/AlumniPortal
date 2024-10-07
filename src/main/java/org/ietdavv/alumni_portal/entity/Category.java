package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "category_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;
    @Column(name = "color", nullable = false, columnDefinition = "varchar(7)")
    private String color;
    @Column(name = "cat_title")
    private String category;
    @OneToMany(mappedBy = "category")
    private List<Blog> blogs;
}
