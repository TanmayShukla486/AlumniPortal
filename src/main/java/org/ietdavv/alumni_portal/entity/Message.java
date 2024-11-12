package org.ietdavv.alumni_portal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "message_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sender")
    private String sender;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "content")
    private String content;
    @Column(name = "created")
    private String created;
    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private ChatRoom chatRoom;

}
