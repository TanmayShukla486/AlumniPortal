package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.dto.CategoryDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryServiceInterface {

    ResponseEntity<ResponseDTO<List<CategoryDTO>>> getAllCategories();
    ResponseEntity<ResponseDTO<CategoryDTO>> addCategory(CategoryDTO category);
    ResponseEntity<ResponseDTO<String>> removeCategory(String category);
    ResponseEntity<ResponseDTO<Long>> getBlogCount(CategoryDTO category);

}
