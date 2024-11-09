package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Company;
import org.ietdavv.alumni_portal.entity.PortalUser;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompactAlumniDTO implements Comparable<CompactAlumniDTO>{

    private String name;
    private int graduationYear;
    private int yearsOfExp;
    private int followers;
    private String username;

    public static CompactAlumniDTO mapToDTO(PortalUser user) {
        int yearsOfExp = 0;
        String middleName = user.getMiddleName() == null ? "": user.getMiddleName();
        for(Company company: user.getAlumniDetails().getCompanies()) yearsOfExp += company.getTimeSpent();
        return CompactAlumniDTO.builder()
                .yearsOfExp(yearsOfExp)
                .graduationYear(user.getGraduationYear())
                .name(user.getFirstName() + (middleName.isEmpty() ? "" : " " + middleName) + " " + user.getLastName())
                .followers(user.getFollowers() == null ? 0 :user.getFollowers().size())
                .username(user.getUsername())
                .build();
    }

    @Override
    public int compareTo(CompactAlumniDTO o) {
        return this.graduationYear - o.graduationYear;
    }
}
