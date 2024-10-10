package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.*;
import org.ietdavv.alumni_portal.entity.*;
import org.ietdavv.alumni_portal.error_handling.errors.RoleNotFoundException;
import org.ietdavv.alumni_portal.repository.AlumniDetailsRepository;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AlumniDetailsRepository alumniDetailsRepository;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService(UserRepository repository, JwtService jwtService, AlumniDetailsRepository alumniDetailsRepository, AuthenticationManager authManager) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.alumniDetailsRepository = alumniDetailsRepository;
        this.authManager = authManager;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<CompactUserDTO>> registerUser(RegisterUserDTO user) {
        Role role = switch (user.getRole()) {
            case "student" -> Role.ROLE_STUDENT;
            case "alumni" -> Role.ROLE_ALUMNI;
            case "admin" -> Role.ROLE_ADMIN;
            default -> throw new RoleNotFoundException("Invalid role assigned");
        };

        PortalUser.PortalUserBuilder builder = PortalUser.builder()
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(encoder.encode(user.getPassword()))
                .bio(user.getBio())
                .role(role)
                .graduationYear(user.getGraduationYear());

        PortalUser toBeSaved = builder.build();
        Optional.ofNullable(user.getAchievements())
                .ifPresent(list -> {
                    List<Achievement> achievements = list.stream()
                            .map(Achievement::mapToEntity)
                            .peek(achievement -> achievement.setUser(toBeSaved))
                            .toList();
                    toBeSaved.setAchievements(achievements);
                });
        Optional.ofNullable(user.getCompanies())
                .ifPresent(list -> {
                    List<Company> companies = list.stream()
                            .map(Company::mapToEntity)
                            .toList();
                    AlumniDetails alumniDetails = AlumniDetails.builder()
                            .companies(companies)
                            .build();
                    alumniDetails.setUser(toBeSaved);
                    companies.forEach(company -> company.setAlumni(alumniDetails));
                    toBeSaved.setAlumniDetails(alumniDetails);

                });
        PortalUser saved = repository.save(toBeSaved);
        return ResponseEntity.ok(ResponseDTO.<CompactUserDTO>builder()
                .statusCode(200)
                .message("Successful")
                .data(CompactUserDTO.mapToDTO(saved))
                .build()
        );

    }

    @Override
    public ResponseEntity<ResponseDTO<UserDTO>> getUserDetailsByUsername(String username) {
        PortalUser user = repository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));
        return ResponseEntity.ok(ResponseDTO.<UserDTO>builder()
                .statusCode(200)
                .message("Successful")
                .data(UserDTO.mapToDTO(user))
                .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<String>> loginUser(LoginUserDTO user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            PortalUser portalUser = repository
                    .findByUsername(user.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("No user found"));
            return ResponseEntity.ok(ResponseDTO.<String>builder()
                    .statusCode(200)
                    .message("Login successful")
                    .data(jwtService.generateToken(portalUser))
                    .build()
            );
        }
        return ResponseEntity.badRequest().body(
                ResponseDTO.<String>builder()
                        .statusCode(401)
                        .message("Invalid credentials")
                        .data("Could not login user with the given credential. Recheck and try again")
                        .build()
        );
    }
}
