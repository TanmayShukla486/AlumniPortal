package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.EventDTO;
import org.ietdavv.alumni_portal.entity.Event;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.EventRepository;
import org.ietdavv.alumni_portal.service.interfaces.EventServiceInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventService implements EventServiceInterface {

    private final EventRepository eventRepository;

    @Override
    public ResponseEntity<List<EventDTO>> getEvents() {
        return ResponseEntity.ok(eventRepository.findAll().stream().map(EventDTO::mapToDTO).toList());
    }

    @Override
    @Transactional
    public ResponseEntity<String> createEvent(EventDTO dto) {
        Event event = Event.builder()
                .eventTitle(dto.getTitle())
                .eventContent(dto.getContent())
                .date(dto.getDate())
                .build();
        eventRepository.save(event);
        return ResponseEntity.ok(ResponseMessage.SUCCESS);
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.EVENT_NOT_FOUND));
        eventRepository.delete(event);
        return ResponseEntity.ok(ResponseMessage.SUCCESS);
    }
}
