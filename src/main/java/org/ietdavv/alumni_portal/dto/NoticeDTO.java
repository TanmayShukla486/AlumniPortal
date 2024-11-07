package org.ietdavv.alumni_portal.dto;


import lombok.*;
import org.ietdavv.alumni_portal.entity.Notice;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NoticeDTO {

    private Long id;
    private String title;
    private String content;

    public static NoticeDTO mapToDTO(Notice notice) {
        return NoticeDTO.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}
