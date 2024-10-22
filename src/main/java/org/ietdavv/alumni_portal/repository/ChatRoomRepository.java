package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.ChatRoom;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


    Optional<ChatRoom> findBySenderAndReceiver(String sender, String receiver);
    List<ChatRoom> findBySenderOrReceiver(String sender, String receiver);

}
