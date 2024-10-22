package org.ietdavv.alumni_portal.service;

import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.entity.ChatRoom;
import org.ietdavv.alumni_portal.entity.Message;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.MessageRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.MessageServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService implements MessageServiceInterface {

    private final MessageRepository repository;
    private final UserRepository userRepository;
    private final ChatRoomService chatRoomService;

    @Override
    public Message save(Message message) {
        userRepository
                .findByUsername(message.getReceiver())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        userRepository
                .findByUsername(message.getSender())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        ChatRoom room = chatRoomService
                .findBySenderAndReceiver(message.getSender(), message.getReceiver(), true)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.CHATROOM_NOT_FOUND));
        message.setRoom(room);
        return repository.save(message);
    }

    @Override
    public List<Message> findMessages(String sender, String receiver) {
        ChatRoom room = chatRoomService.findBySenderAndReceiver(sender, receiver, false)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.CHATROOM_NOT_FOUND));
        return room.getMessages();
    }
}
