package org.ietdavv.alumni_portal.controller;

import org.ietdavv.alumni_portal.dto.ReplyDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ReplyController {

    private final ReplyService replyService;

    @Autowired
    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @GetMapping("/comment/{commentId}/replies")
    public ResponseEntity<ResponseDTO<List<ReplyDTO>>> getAllReplies(@PathVariable("commentId") Long commentId) {
        return replyService.getRepliesByComment(commentId);
    }

    @PostMapping("/comment/{commentId}/replies")
    public ResponseEntity<ResponseDTO<String>> addReply(
            @PathVariable("commentId") Long commentId,
            @RequestBody ReplyDTO dto) {
        dto.setCommentId(commentId);
        return replyService.addReply(dto);
    }

    @DeleteMapping("/comment/{commentId}/replies/{replyId}")
    public ResponseEntity<ResponseDTO<String>> deleteReply(
            @PathVariable Long commentId,
            @PathVariable Long replyId
            ) {
        return replyService.deleteReply(replyId);
    }
}
