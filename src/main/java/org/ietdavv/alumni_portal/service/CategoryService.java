package org.ietdavv.alumni_portal.service;

import org.ietdavv.alumni_portal.dto.CategoryDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.Category;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.BlogRepository;
import org.ietdavv.alumni_portal.repository.CategoryRepository;
import org.ietdavv.alumni_portal.service.interfaces.CategoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CategoryService implements CategoryServiceInterface {

    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(BlogRepository blogRepository, CategoryRepository categoryRepository) {
        this.blogRepository = blogRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<ResponseDTO<List<CategoryDTO>>> getAllCategories() {
        List<CategoryDTO> categoryList = categoryRepository
                .findAll()
                .stream()
                .map(CategoryDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<CategoryDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(categoryList)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<CategoryDTO>> addCategory(CategoryDTO category) {
        Category toBeSaved = Category.builder()
                .category(category.getCategoryTitle())
                .color(category.getColor())
                .build();
        CategoryDTO saved = CategoryDTO.mapToDTO(categoryRepository.save(toBeSaved));
        return ResponseEntity.ok(
                ResponseDTO.<CategoryDTO>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(saved)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> removeCategory(String category) {
        Category category1 = categoryRepository
                .findByCategory(category)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.CATEGORY_NOT_FOUND));
        categoryRepository.delete(category1);
        return ResponseEntity.ok(
                ResponseDTO.<String>builder()
                        .statusCode(204)
                        .message(ResponseMessage.)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<Long>> getBlogCount(CategoryDTO category) {
        return null;
    }
}