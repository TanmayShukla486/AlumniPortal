package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.entity.Message;

import java.util.List;


public interface MessageServiceInterface {

    Message save(Message message);

    List<Message> findMessages(String sender, String receiver);
}
