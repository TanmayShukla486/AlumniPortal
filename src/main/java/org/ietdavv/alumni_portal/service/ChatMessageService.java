package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.ChatRoomDTO;
import org.ietdavv.alumni_portal.entity.ChatRoom;
import org.ietdavv.alumni_portal.entity.Message;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.ChatRoomRepository;
import org.ietdavv.alumni_portal.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Message saveMessage(Message message) {
        String chatRoomId = ChatRoom.generateId(message.getSender(), message.getReceiver());
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseGet(() -> {
                    ChatRoom other = ChatRoom.builder()
                            .id(ChatRoom.generateId(message.getSender(), message.getReceiver()))
                            .build();
                    return chatRoomRepository.save(other);
                });
        message.setChatRoom(chatRoom);
       return messageRepository.save(message);
    }

    public List<ChatRoomDTO> getChatRooms() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ChatRoom> chatRooms =  chatRoomRepository.findByIdContaining(username);
        chatRooms.sort((a,b) -> b.getMessages().size() - a.getMessages().size());
        return chatRooms
                .stream()
                .map(ChatRoomDTO::mapToDTO)
                .toList();
    }

    public List<Message> getMessages(String sender, String receiver) {
        String chatRoomId = ChatRoom.generateId(sender, receiver);
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.CHATROOM_NOT_FOUND));
        return chatRoom.getMessages();
    }

}
