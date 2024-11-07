package org.ietdavv.alumni_portal.controller;

import lombok.AllArgsConstructor;
import org.ietdavv.alumni_portal.dto.EventDTO;
import org.ietdavv.alumni_portal.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

    private final EventService eventService;

    // Event Handling
    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getEvents() {
        return eventService.getEvents();
    }

    @PostMapping("/events")
    public ResponseEntity<String> addEvent(@RequestBody EventDTO eventDTO){
        return eventService.createEvent(eventDTO);
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable(name = "id") Long id) {
        return eventService.deleteEvent(id);
    }
}
