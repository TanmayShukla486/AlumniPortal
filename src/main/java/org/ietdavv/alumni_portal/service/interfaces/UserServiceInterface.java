package org.ietdavv.alumni_portal.service.interfaces;

import org.ietdavv.alumni_portal.dto.*;
import org.springframework.http.ResponseEntity;

public interface UserServiceInterface {

    ResponseEntity<ResponseDTO<CompactUserDTO>> registerUser(RegisterUserDTO user);
    ResponseEntity<ResponseDTO<UserDTO>> getUserDetailsByUsername(String username);
    ResponseEntity<ResponseDTO<String>> loginUser(LoginUserDTO user);
}
