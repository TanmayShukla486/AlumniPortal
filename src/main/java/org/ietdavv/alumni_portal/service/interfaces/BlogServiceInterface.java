package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.BlogDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.Blog;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogServiceInterface {

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getAllBlogs();
    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByCategory(String category);

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByAuthorAndCategory(String username, String category);
    ResponseEntity<ResponseDTO<BlogDTO>> getBlogById(Long id);

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getLatestBlogs();


    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByAuthor(String username);
    ResponseEntity<ResponseDTO<String>> postBlog(BlogDTO blog);
    ResponseEntity<ResponseDTO<String>> deleteBlog(Long blogId);

    ResponseEntity<ResponseDTO<Long>> getBlogCountByCategory(String category);
    ResponseEntity<ResponseDTO<Long>> getBlogCountByAuthor(String author);
    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogByTitle(String title);

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogByTitleAndCategory(String category, String title);

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByAuthorAndTitle(String username, String title);

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByAuthorAndTitleAndCategory(String username, String title, String category);

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getPopularBlogs();
}
