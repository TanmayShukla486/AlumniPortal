package org.ietdavv.alumni_portal.error_handling;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.dto.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//@ControllerAdvice
//@Slf4j
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler
//    public ResponseEntity<ResponseDTO<String>> handleException(Exception e) {
//        log.debug("Error : {}", e.getMessage());
//        return ResponseEntity.status(500)
//                .body(ResponseDTO.<String>builder()
//                        .statusCode(500)
//                        .message("ERROR")
//                        .message(e.getMessage())
//                        .build());
//    }
//
//}
