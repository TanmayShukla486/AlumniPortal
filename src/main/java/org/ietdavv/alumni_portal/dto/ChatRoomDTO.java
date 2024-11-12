package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.ChatRoom;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
@ToString
@Builder
public class ChatRoomDTO {

    private String sender;
    private String receiver;

    public static ChatRoomDTO mapToDTO(ChatRoom chatRoom) {
        String[] participants = chatRoom.getId().split("_");
        return ChatRoomDTO.builder()
                .sender(participants[0])
                .receiver(participants[1])
                .build();
    }

}
