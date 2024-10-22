package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "chat_room_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;
    @CreationTimestamp
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "sender_username")
    private String sender;
    @Column(name = "receiver_username")
    private String receiver;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Message> messages;
}
