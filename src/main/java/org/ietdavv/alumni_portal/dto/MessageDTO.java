package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Message;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MessageDTO {

    private String sender;
    private String receiver;
    private String content;
    private Long id;

    public static MessageDTO mapToDTO(Message message) {
        return MessageDTO.builder()
                .id(message.getId())
                .sender(message.getSender())
                .receiver(message.getReceiver())
                .content(message.getContent())
                .build();
    }

}
