package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.BlogDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BlogServiceInterface {

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getAllBlogs();
    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByCategory(String category);

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByAuthorAndCategory(String username, String category);

    ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByAuthor(String username);
    ResponseEntity<ResponseDTO<String>> postBlog(BlogDTO blog);
    ResponseEntity<ResponseDTO<String>> deleteBlog(Long blogId);

}
