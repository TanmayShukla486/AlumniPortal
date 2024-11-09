package org.ietdavv.alumni_portal.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
@Table(name = "event_tbl")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "event_title")
    private String eventTitle;
    @Column(name = "event_content")
    private String eventContent;
    @Column(name = "event_date")
    private String date;
    @CreationTimestamp
    @Column(name = "created_at", columnDefinition = "timestamptz default now()")
    private Timestamp createdAt;
    @UpdateTimestamp
    @Column(name = "updated_at", columnDefinition = "timestamptz default now()")
    private Timestamp updatedAt;
}
