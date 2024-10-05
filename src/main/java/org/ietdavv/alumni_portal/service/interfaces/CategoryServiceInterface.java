package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

public interface CategoryServiceInterface {

    ResponseEntity<ResponseDTO<CategoryDTO>> getAllCategories();
    ResponseEntity<ResponseDTO<CategoryDTO>> addCategory(String category);
    ResponseEntity<ResponseDTO<CategoryDTO>> removeCategory(String category);
    ResponseEntity<ResponseDTO<Long>> getBlogCount(CategoryDTO category);

}
