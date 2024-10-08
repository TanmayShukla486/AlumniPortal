package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.BlogDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.Blog;
import org.ietdavv.alumni_portal.entity.Category;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.error_handling.errors.UnAuthorizedCommandException;
import org.ietdavv.alumni_portal.repository.BlogRepository;
import org.ietdavv.alumni_portal.repository.CategoryRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.BlogServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService implements BlogServiceInterface {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getAllBlogs() {
        List<BlogDTO> blogs = blogRepository.findAll().stream().map(BlogDTO::mapToDTO).toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<BlogDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(blogs)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByCategory(String category) {
        Category cat = categoryRepository
                .findByCategory(category)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.CATEGORY_NOT_FOUND));
        List<BlogDTO> blogs = blogRepository
                .findByCategory(cat)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND))
                .stream()
                .map(BlogDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<BlogDTO>>builder()
                        .statusCode(200)
                        .data(blogs)
                        .message(ResponseMessage.SUCCESS)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByAuthorAndCategory(String username, String category) {
        Category cat = categoryRepository
                .findByCategory(category)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.CATEGORY_NOT_FOUND));
        final PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        List<BlogDTO> blogs = blogRepository
                .findByAuthorAndCategory(user, cat)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND))
                .stream()
                .map(BlogDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<BlogDTO>>builder()
                        .statusCode(200)
                        .data(blogs)
                        .message(ResponseMessage.SUCCESS)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getBlogsByAuthor(String username) {

        final PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        List<BlogDTO> blogs = blogRepository
                .findByAuthor(user)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND))
                .stream()
                .map(BlogDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<BlogDTO>>builder()
                        .statusCode(200)
                        .data(blogs)
                        .message(ResponseMessage.SUCCESS)
                        .build()
        );

    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<String>> postBlog(BlogDTO dto) {

        final PortalUser user = userRepository
                .findByUsername(dto.getAuthor())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));

        final Category category = categoryRepository
                .findByCategory(dto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.CATEGORY_NOT_FOUND));

        Blog blog = Blog.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(user)
                .category(category)
                .commentsEnabled(dto.isCommentsEnabled())
                .build();

        blogRepository.save(blog);

        return ResponseEntity.ok(
                ResponseDTO.<String>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(ResponseMessage.BLOG_CREATED)
                        .build()
        );
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseDTO<String>> deleteBlog(Long blogId) {
        Blog blog = blogRepository
                .findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND));
        PortalUser user = userRepository
                .findByUsername(
                        SecurityContextHolder.getContext().getAuthentication().getName()
                )
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        if (blog.getAuthor().equals(user))
            blogRepository.delete(blog);
        else throw new UnAuthorizedCommandException(ResponseMessage.UNAUTHORIZED);
        return ResponseEntity.ok(
                ResponseDTO.<String>builder()
                        .statusCode(204)
                        .message(ResponseMessage.SUCCESS)
                        .data(ResponseMessage.BLOG_DELETED)
                        .build()
        );
    }
}
