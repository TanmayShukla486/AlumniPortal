package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.EventDTO;
import org.ietdavv.alumni_portal.entity.Event;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EventServiceInterface {

    ResponseEntity<List<EventDTO>> getEvents();
    ResponseEntity<String> createEvent(EventDTO event);
    ResponseEntity<String> deleteEvent(Long id);

}
