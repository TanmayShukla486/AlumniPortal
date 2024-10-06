package org.ietdavv.alumni_portal.dto;

import lombok.*;
import org.ietdavv.alumni_portal.entity.Category;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CategoryDTO {

    private String categoryTitle;
    private String color;

    public static CategoryDTO mapToDTO(Category category) {
        return CategoryDTO.builder()
                .categoryTitle(category.getCategory())
                .color(category.getColor())
                .build();
    }

}