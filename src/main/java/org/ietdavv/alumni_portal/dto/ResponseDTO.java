package org.ietdavv.alumni_portal.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ResponseDTO <T> {

    private int statusCode;
    private String message;
    private T data;

}
