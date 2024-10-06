package org.ietdavv.alumni_portal.service;

import org.ietdavv.alumni_portal.dto.ReplyDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.ietdavv.alumni_portal.entity.Comment;
import org.ietdavv.alumni_portal.entity.PortalUser;
import org.ietdavv.alumni_portal.entity.Reply;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.repository.CommentRepository;
import org.ietdavv.alumni_portal.repository.ReplyRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.ReplyServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyService implements ReplyServiceInterface {

    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public ReplyService(ReplyRepository replyRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.replyRepository = replyRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }


    @Override
    public ResponseEntity<ResponseDTO<List<ReplyDTO>>> getRepliesByComment(long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.COMMENT_NOT_FOUND));
        List<ReplyDTO> replies = comment.getReplies()
                .stream()
                .map(ReplyDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(ResponseDTO.<List<ReplyDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(replies)
                .build());
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> addReply(ReplyDTO reply) {

        PortalUser user = userRepository
                .findByUsername(reply.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Comment comment = commentRepository
                .findById(reply.getCommentId())
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.COMMENT_NOT_FOUND));

        Reply toBeSaved = Reply.builder()
                .comment(comment)
                .replier(user)
                .content(reply.getContent())
                .build();
        replyRepository.save(toBeSaved);
        return ResponseEntity.ok(
                ResponseDTO.<String>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(ResponseMessage.REPLY_ADDED)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> deleteReply(long replyId) {
        Reply reply = replyRepository
                .findById(replyId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.REPLY_NOT_FOUND));
        replyRepository.delete(reply);
        return ResponseEntity.ok(
                ResponseDTO.<String>builder()
                        .statusCode(204)
                        .message(ResponseMessage.SUCCESS)
                        .data(ResponseMessage.REPLY_DELETED)
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<List<ReplyDTO>>> getReplyByUserAndComment(long commentId, String username) {
        PortalUser user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        Comment comment = commentRepository
                .findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.COMMENT_NOT_FOUND));
        List<ReplyDTO> replyList = replyRepository
                .findByCommentAndReplier(comment, user)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.REPLY_NOT_FOUND))
                .stream()
                .map(ReplyDTO::mapToDTO)
                .toList();
        return ResponseEntity.ok(
                ResponseDTO.<List<ReplyDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(replyList)
                        .build()
        );
    }
}
