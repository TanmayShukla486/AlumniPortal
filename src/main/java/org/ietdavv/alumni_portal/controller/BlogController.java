package org.ietdavv.alumni_portal.controller;

import org.ietdavv.alumni_portal.dto.BlogDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping("/blog")
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getAllBlogs(
            @PathVariable(name = "author", required = false) String username,
            @PathVariable(name = "category", required = false) String category
    ) {
        if (username == null && category == null) return blogService.getAllBlogs();
        else if (username != null && category != null)
            return blogService.getBlogsByAuthorAndCategory(username, category);
        else if (username == null) return blogService.getBlogsByCategory(category);
        return blogService.getBlogsByAuthor(username);
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<ResponseDTO<BlogDTO>> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping("/blog")
    @PreAuthorize("hasRole('ROLE_ALUMNI')")
    public ResponseEntity<ResponseDTO<String>> postBlog(@RequestBody BlogDTO blog) {
        return blogService.postBlog(blog);
    }

    @DeleteMapping("/blog")
    @PreAuthorize("hasAnyRole('ROLE_ALUMNI', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<String>> deleteBlog(@PathVariable(name = "id") Long blogId) {
        return blogService.deleteBlog(blogId);
    }

}
