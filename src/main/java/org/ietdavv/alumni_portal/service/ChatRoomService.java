package org.ietdavv.alumni_portal.service;


import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.entity.ChatRoom;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.ChatRoomRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.ChatRoomServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ChatRoomService implements ChatRoomServiceInterface {

    private final ChatRoomRepository roomRepository;
    private final UserRepository userRepository;


    @Override
    public List<ChatRoom> findBySenderOrReceiver(String user) {
        return roomRepository
                .findBySenderOrReceiver(user, user);
    }

    @Override
    public Optional<ChatRoom> findBySenderAndReceiver(String sender, String receiver, boolean create) {
        userRepository
                .findByUsername(sender)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        userRepository
                .findByUsername(receiver)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        return roomRepository
                .findBySenderAndReceiver(sender, receiver)
                .or(() -> {
                   if (create) {
                       ChatRoom chatRoom = ChatRoom.builder()
                               .sender(sender)
                               .receiver(receiver)
                               .build();
                       return Optional.of(roomRepository.save(chatRoom));
                   }
                   return Optional.empty();
                });
    }
}
