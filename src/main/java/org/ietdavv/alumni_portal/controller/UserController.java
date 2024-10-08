package org.ietdavv.alumni_portal.controller;

import org.ietdavv.alumni_portal.dto.*;
import org.ietdavv.alumni_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
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
        return userService.registerUser(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<String>> loginUser(@RequestBody LoginUserDTO dto) {
        return userService.loginUser(dto);
    }
}
