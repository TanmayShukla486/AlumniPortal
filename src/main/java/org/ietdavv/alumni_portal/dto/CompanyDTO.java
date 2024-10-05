package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Company;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CompanyDTO {

    private String company;
    private int timeSpent;
    private boolean currentlyWorking;

    public static CompanyDTO mapToDTO(Company company) {
        return CompanyDTO.builder()
                .company(company.getName())
                .timeSpent(company.getTimeSpent())
                .currentlyWorking(company.isCurrentlyWorking())
                .build();
    }

}
