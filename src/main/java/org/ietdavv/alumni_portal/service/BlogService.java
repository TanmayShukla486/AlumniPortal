package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.ietdavv.alumni_portal.dto.BlogDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.*;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.error_handling.errors.UnAuthorizedCommandException;
import org.ietdavv.alumni_portal.repository.BlogRepository;
import org.ietdavv.alumni_portal.repository.CategoryRepository;
import org.ietdavv.alumni_portal.repository.LikeRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.BlogServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BlogService implements BlogServiceInterface {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LikeRepository likeRepository;



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
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getLatestBlogs() {
        return ResponseEntity.ok(ResponseDTO.<List<BlogDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(
                                blogRepository
                                        .findAllByOrderByCreatedAtDesc()
                                        .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND))
                                        .stream()
                                        .map(BlogDTO::mapToDTO)
                                        .toList()
                        )
                .build());
    }

    @Override
    public ResponseEntity<ResponseDTO<List<BlogDTO>>> getLowestLikedBlogs() {
        return ResponseEntity.ok(
                ResponseDTO.<List<BlogDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(blogRepository
                                .findAllOrderByLikesAsc()
                                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND))
                                .stream()
                                .map(BlogDTO::mapToDTO)
                                .toList())
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<BlogDTO>> getBlogById(Long id) {
        return ResponseEntity.ok(
                ResponseDTO.<BlogDTO>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(
                                BlogDTO.mapToDTO(
                                        blogRepository.findById(id)
                                                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.BLOG_NOT_FOUND))
                                )
                        )
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
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        final PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));

        final Category category = categoryRepository
                .findByCategory(dto.getCategory())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.CATEGORY_NOT_FOUND));
        System.out.println(category.toString());

        Blog blog = Blog.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(user)
                .category(category)
                .commentsEnabled(dto.isCommentsEnabled())
                .build();

        Blog saved = blogRepository.save(blog);
        final Like like = Like.builder()
                .likedBy(user)
                .type(LikeEntity.BLOG)
                .entityId(saved.getId())
                .build();
        likeRepository.save(like);
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
        else if (user.getRole().equals(Role.ROLE_ALUMNI))
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
