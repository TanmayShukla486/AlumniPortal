package org.ietdavv.alumni_portal.controller;

import org.ietdavv.alumni_portal.dto.CategoryDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<CategoryDTO>> addCategory(CategoryDTO category) {
        return categoryService.addCategory(category);
    }

    @DeleteMapping("/category")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<String>> deleteCategory(@RequestParam(name = "name") String category) {
        return categoryService.removeCategory(category);
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseDTO<List<CategoryDTO>>> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/category/{category}/count")
    public ResponseEntity<ResponseDTO<Long>> getBlogCount(@PathVariable("category") String category) {
        return categoryService.getBlogCount(category);
    }

}
