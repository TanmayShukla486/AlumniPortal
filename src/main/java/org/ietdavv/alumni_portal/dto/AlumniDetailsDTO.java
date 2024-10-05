package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.AlumniDetails;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AlumniDetailsDTO {

    private List<CompanyDTO> companies;

    public static AlumniDetailsDTO mapToDTO(AlumniDetails details) {
        return AlumniDetailsDTO.builder()
                .companies(details.getCompanies().stream().map(CompanyDTO::mapToDTO).toList())
                .build();
    }

}
