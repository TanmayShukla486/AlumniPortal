package org.ietdavv.alumni_portal.repository;

import org.ietdavv.alumni_portal.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
