package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Event;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EventDTO {

    private Long id;
    private String title;
    private String content;
    private String date;

    public static EventDTO mapToDTO(Event event) {
        return EventDTO.builder()
                .id(event.getId())
                .title(event.getEventTitle())
                .content(event.getEventContent())
                .date(event.getDate())
                .build();
    }

}
