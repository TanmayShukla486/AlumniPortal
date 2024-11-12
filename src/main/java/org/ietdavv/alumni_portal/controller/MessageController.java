package org.ietdavv.alumni_portal.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.service.ChatMessageService;
import org.ietdavv.alumni_portal.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/chat")
public class MessageController {

    private final ChatMessageService service;
    private final UserService userService;

    @GetMapping("/history")
    public ResponseEntity<?> getMessages(@RequestParam(name = "sender") String sender,
                                         @RequestParam(name = "recipient") String receiver) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!username.equals(sender) && !username.equals(receiver)) {
            return ResponseEntity.status(403).body("Unverified User");
        }
        return ResponseEntity.ok(service.getMessages(sender, receiver));
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getRooms() {
        return ResponseEntity.ok(service.getChatRooms());
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsersByUsername(@RequestParam(name = "username")String username) {
        if (username == null) return ResponseEntity.status(400).body(ResponseMessage.INVALID_ENTITY);
        return userService.getUsersByUsername(username);
    }

}
