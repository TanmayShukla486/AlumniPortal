package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.*;
import org.ietdavv.alumni_portal.entity.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserServiceInterface {

    ResponseEntity<ResponseDTO<CompactUserDTO>> registerUser(RegisterUserDTO user);
    ResponseEntity<ResponseDTO<UserDTO>> getUserDetailsByUsername(String username);
    ResponseEntity<ResponseDTO<LoginResponseDTO>> loginUser(LoginUserDTO user);
    ResponseEntity<ResponseDTO<List<UserDTO>>> getUserByRole(String role);

    ResponseEntity<ResponseDTO<List<UserDTO>>> getAllUsers();

    ResponseEntity<ResponseDTO<List<UserDTO>>> getAlumniOfYear(Integer year);

    ResponseEntity<ResponseDTO<Long>> countByRole(String role);

    ResponseEntity<ResponseDTO<List<RecommendedUserDTO>>> getRecommendedAlumni();
}
