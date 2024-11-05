package org.ietdavv.alumni_portal.service;

import jakarta.transaction.Transactional;
import org.ietdavv.alumni_portal.dto.*;
import org.ietdavv.alumni_portal.entity.*;
import org.ietdavv.alumni_portal.error_handling.ResponseMessage;
import org.ietdavv.alumni_portal.error_handling.errors.ResourceNotFoundException;
import org.ietdavv.alumni_portal.error_handling.errors.RoleNotFoundException;
import org.ietdavv.alumni_portal.repository.UserRepository;
import org.ietdavv.alumni_portal.service.interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserService(UserRepository repository, JwtService jwtService, AuthenticationManager authManager) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    private List<PortalUser> getNonFollowing(List<PortalUser> all, Set<Follow> following) {
        List<PortalUser> users = new ArrayList<>();
        Set<String> usernames = following
                .stream()
                .map(user -> user.getFollower().getUsername())
                .collect(Collectors.toSet());
        for (PortalUser user: all) if (!usernames.contains(user.getUsername())) users.add(user);
        return users;
    }

    @Override
    @Transactional
    public ResponseEntity<ResponseDTO<CompactUserDTO>> registerUser(RegisterUserDTO user) {
        Role role = switch (user.getRole()) {
            case "STUDENT" -> Role.ROLE_STUDENT;
            case "ALUMNI" -> Role.ROLE_ALUMNI;
            case "ADMIN" -> Role.ROLE_ADMIN;
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
//        Optional.ofNullable(user.getAchievements())
//                .ifPresent(list -> {
//                    List<Achievement> achievements = list.stream()
//                            .map(Achievement::mapToEntity)
//                            .peek(achievement -> achievement.setUser(toBeSaved))
//                            .toList();
//                    toBeSaved.setAchievements(achievements);
//                });
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
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAlumniOfYear(Integer year) {
        Role role = Role.ROLE_ALUMNI;
        return ResponseEntity.ok(
                ResponseDTO.<List<UserDTO>>builder()
                        .statusCode(200)
                        .message("Successful")
                        .data(repository.findByRoleAndGraduationYear(role, year)
                                .stream()
                                .map(UserDTO::mapToDTO)
                                .toList())
                        .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<Long>> countByRole(String r) {
        Role role = switch (r) {
            case "STUDENT" -> Role.ROLE_STUDENT;
            case "ALUMNI" -> Role.ROLE_ALUMNI;
            case "ADMIN" -> Role.ROLE_ADMIN;
            default -> throw new RoleNotFoundException("Invalid role assigned");
        };
        return ResponseEntity.ok(ResponseDTO
                .<Long>builder()
                .statusCode(200)
                .message(ResponseMessage.SUCCESS)
                .data(repository.countByRole(role)).build());
    }

    @Override
    public ResponseEntity<ResponseDTO<List<RecommendedUserDTO>>> getRecommendedAlumni() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        PortalUser user = repository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.USER_NOT_FOUND));
        List<PortalUser> users = repository.findByGraduationYear(user.getGraduationYear());
        return ResponseEntity.ok(ResponseDTO
                        .<List<RecommendedUserDTO>>builder()
                        .statusCode(200)
                        .message(ResponseMessage.SUCCESS)
                        .data(
                                getNonFollowing(users, user.getFollowing())
                                        .stream()
                                        .map(RecommendedUserDTO::mapToDTO)
                                        .toList()
                        )
                .build()
        );
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getUserByRole(String r) {
        Role role = switch (r) {
            case "STUDENT" -> Role.ROLE_STUDENT;
            case "ALUMNI" -> Role.ROLE_ALUMNI;
            default -> throw new RoleNotFoundException("Invalid role assigned");
        };
        return ResponseEntity.ok(ResponseDTO.<List<UserDTO>>builder()
                .statusCode(200)
                .message(ResponseMessage.SUCCESS)
                .data(
                        repository.findByRole(role)
                                .stream()
                                .map(UserDTO::mapToDTO)
                                .toList()
                )
                .build());
    }

    @Override
    public ResponseEntity<ResponseDTO<List<UserDTO>>> getAllUsers() {
        return ResponseEntity.ok(ResponseDTO.<List<UserDTO>>builder()
                .statusCode(200)
                .message(ResponseMessage.SUCCESS)
                .data(
                        repository.findAll()
                                .stream().map(UserDTO::mapToDTO)
                                .toList()
                )
                .build());
    }


    @Override
    public ResponseEntity<ResponseDTO<LoginResponseDTO>> loginUser(LoginUserDTO user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        PortalUser portalUser = repository
                .findByUsername(user.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));
        String token = jwtService.generateToken(portalUser);
        return ResponseEntity.ok(ResponseDTO.<LoginResponseDTO>builder()
                .statusCode(200)
                .message("Login successful")
                .data(LoginResponseDTO.builder()
                        .username(user.getUsername())
                        .token(token)
                        .refreshToken(null)
                        .role(jwtService.extractRole(token).substring(5))
                        .build())
                .build()
        );
    }
}
