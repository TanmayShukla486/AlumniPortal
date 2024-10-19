package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.ChatRoom;
import org.ietdavv.alumni_portal.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByRoom(ChatRoom room);

}
