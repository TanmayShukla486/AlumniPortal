package org.ietdavv.alumni_portal.controller;

import org.ietdavv.alumni_portal.dto.CommentDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comment/{blogId}")
    public ResponseEntity<ResponseDTO<List<CommentDTO>>> getCommentsForBlog(
            @PathVariable("blogId") Long blogId,
            @RequestParam(name = "username", required = false) String username) {
        if (username != null) return commentService.getCommentByBlogAndUser(blogId, username);
        return commentService.getCommentsOnBlog(blogId);
    }

    @PostMapping("/comment")
    public ResponseEntity<ResponseDTO<CommentDTO>> addComment(@RequestBody CommentDTO dto) {
        return commentService.addComment(dto);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<ResponseDTO<String>> deleteComment(@RequestParam(name = "id") Long commentId) {
        return commentService.deleteComment(commentId);
    }


}
