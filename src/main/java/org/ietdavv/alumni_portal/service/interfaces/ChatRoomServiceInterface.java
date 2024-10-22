package org.ietdavv.alumni_portal.service.interfaces;


import org.ietdavv.alumni_portal.entity.ChatRoom;

import java.util.List;
import java.util.Optional;

public interface ChatRoomServiceInterface {

    List<ChatRoom> findBySenderOrReceiver(String user);
    Optional<ChatRoom> findBySenderAndReceiver(String sender, String receiver, boolean create);
}
