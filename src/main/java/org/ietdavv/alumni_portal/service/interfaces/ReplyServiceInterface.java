package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.ReplyDTO;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReplyServiceInterface {

    ResponseEntity<ResponseDTO<List<ReplyDTO>>> getRepliesByComment(long commentId);
    ResponseEntity<ResponseDTO<String>> addReply(ReplyDTO reply);
    ResponseEntity<ResponseDTO<String>> deleteReply(long replyId);
    ResponseEntity<ResponseDTO<List<ReplyDTO>>> getReplyByUserAndComment(long commentId, String username);

}
