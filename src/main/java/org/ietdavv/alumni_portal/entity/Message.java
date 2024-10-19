package org.ietdavv.alumni_portal.entity;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Message implements Serializable {

    private String sender;
    private String recipient;
    private String content;

}
