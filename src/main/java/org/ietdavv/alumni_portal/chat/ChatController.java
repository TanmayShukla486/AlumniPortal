package org.ietdavv.alumni_portal.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.dto.MessageDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.Message;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.logging.Logger;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void processMessage(@Payload Message message) {
        Message saved = messageService.save(message);
        messagingTemplate
                .convertAndSendToUser(
                        saved.getReceiver(),
                        "/queue/messages",
                        MessageDTO.mapToDTO(saved)
                );
    }

    @GetMapping("/messages/{sender}/{receiver}")
    public ResponseEntity<ResponseDTO<List<MessageDTO>>> getMessages(@PathVariable(name = "receiver") String receiver,
                                                                     @PathVariable(name = "sender") String sender) {
        return ResponseEntity.ok(
                ResponseDTO.<List<MessageDTO>>builder()
                        .message(ResponseMessage.SUCCESS)
                        .statusCode(200)
                        .data(
                                messageService.findMessages(sender, receiver)
                                        .stream().map(MessageDTO::mapToDTO)
                                        .toList()
                        )
                        .build()
        );
    }

}
