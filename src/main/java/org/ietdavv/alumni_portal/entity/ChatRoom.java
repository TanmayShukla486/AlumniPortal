package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "chat_room_tbl")
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    PortalUser sender;
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    PortalUser receiver;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Message> messages;
}
