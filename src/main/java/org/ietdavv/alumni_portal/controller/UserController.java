package org.ietdavv.alumni_portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.ietdavv.alumni_portal.dto.*;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{username}")
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

    @GetMapping("/user")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getUserByRole(
            @RequestParam(name = "role", required = false) String role) {
        if (role == null) return userService.getAllUsers();
        if (role.equals("ADMIN")) return ResponseEntity.badRequest().body(ResponseDTO.<List<UserDTO>>builder()
                        .statusCode(403)
                        .message(ResponseMessage.ACCESS_DENIED)
                        .data(List.of())
                .build());
        return userService.getUserByRole(role);
    }

    @GetMapping("/alumni")
    public ResponseEntity<List<CompactAlumniDTO>> getAlumniByNameAndYear(@RequestParam(name = "name", required = false) String name,
                                                    @RequestParam(name = "year", required = false) Integer year) {
        if (name == null && year == null) return userService.getAlumni();
        if (name != null && year != null)
            return userService.getAlumniByNameAndYear(name, year);
        else if (year == null) return userService.getAlumniByName(name);
        return userService.getAlumniByYear(year);
    }

    @GetMapping("/user/alumni/{year}")
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAlumni(
            @PathVariable(name = "year") Integer year) {
        return userService.getAlumniOfYear(year);
    }

    @GetMapping("/user/recommended")
    public ResponseEntity<ResponseDTO<List<RecommendedUserDTO>>> getRecommended() {
        return userService.getRecommendedAlumni();
    }

}
