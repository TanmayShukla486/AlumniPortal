package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.ChatRoom;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    List<ChatRoom> findBySender(PortalUser sender);
    List<ChatRoom> findByRecipient(PortalUser recipient);

}
