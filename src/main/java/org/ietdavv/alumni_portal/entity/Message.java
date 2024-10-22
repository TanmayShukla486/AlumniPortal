package org.ietdavv.alumni_portal.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "message_tbl")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sender_username")
    private String sender;
    @Column(name = "receiver_username")
    private String receiver;
    @Column(name = "message_content")
    @Lob
    private String content;
    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom room;
}
