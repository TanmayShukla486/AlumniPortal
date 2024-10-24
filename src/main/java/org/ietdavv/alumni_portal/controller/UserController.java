package org.ietdavv.alumni_portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.dto.*;
import org.ietdavv.alumni_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseDTO<UserDTO>> getUserByUsername(@PathVariable String username) {
        return userService.getUserDetailsByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<CompactUserDTO>> registerUser(@RequestBody RegisterUserDTO dto) {
        log.debug("Register Input: {}", dto.toString());
        return userService.registerUser(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> loginUser(@RequestBody LoginUserDTO dto) {
        return userService.loginUser(dto);
    }
}
