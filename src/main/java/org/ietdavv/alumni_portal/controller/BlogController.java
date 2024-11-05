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
            @RequestParam(name = "author", required = false) String username,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "title", required = false) String title
    ) {
        if (username == null && category == null && title == null) return blogService.getAllBlogs();
        else if (category == null && username == null) return blogService.getBlogByTitle(title);
        else if (username == null && title == null) return blogService.getBlogsByCategory(category);
        else if (category == null && title == null) return blogService.getBlogsByAuthor(username);
        else if (username == null) return blogService.getBlogByTitleAndCategory(category, title);
        else if (category == null) return blogService.getBlogsByAuthorAndTitle(username, title);
        else if (title == null) return blogService.getBlogsByAuthorAndCategory(username, category);
        return blogService.getBlogsByAuthorAndTitleAndCategory(username, title, category);
    }

    @GetMapping("/blog/{id}")
    public ResponseEntity<ResponseDTO<BlogDTO>> getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @GetMapping("/blog/category/{category}/count")
    public ResponseEntity<ResponseDTO<Long>> getBlogCountByCategory(@PathVariable String category) {
        return blogService.getBlogCountByCategory(category);
    }

    @GetMapping("/blog/author/{author}/count")
    public ResponseEntity<ResponseDTO<Long>> getBlogCountByAuthor(@PathVariable String author) {
        return blogService.getBlogCountByAuthor(author);
    }

    @GetMapping("/blog/latest")
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getLatestBlogs() {
        return blogService.getLatestBlogs();
    }

    @GetMapping("/blog/popular")
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getPopularBlogs() {
        return blogService.getPopularBlogs();
    }

    @PostMapping("/blog")
    @PreAuthorize("hasRole('ROLE_ALUMNI')")
    public ResponseEntity<ResponseDTO<String>> postBlog(@RequestBody BlogDTO blog) {
        return blogService.postBlog(blog);
    }


    @DeleteMapping("/blog/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ALUMNI', 'ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<String>> deleteBlog(@PathVariable(name = "id") Long blogId) {
        return blogService.deleteBlog(blogId);
    }

}
