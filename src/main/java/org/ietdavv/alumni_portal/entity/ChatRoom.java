package org.ietdavv.alumni_portal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "chatroom_tbl")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatRoom {

    @Id
    private String id;
    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Message> messages;

    public static String generateId(String sender, String receiver) {
        return sender.compareTo(receiver) > 0 ? receiver + "_" + sender : sender + "_" + receiver;
    }

}
