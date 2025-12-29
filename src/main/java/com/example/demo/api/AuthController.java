package com.example.demo.api;

import com.example.demo.dto.user.*;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRegisterRequestDto requestDto) {
        boolean success = userService.registerUser(requestDto);
        if (success) {
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Registration failed (email exists or passwords mismatch)", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> changePassword(@RequestBody UserChangePasswordRequestDto requestDto) {
        boolean success = userService.changePassword(requestDto);
        if (success) {
            return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Change password failed (wrong old password or mismatch)", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> getProfile() {
        UserResponseDto profile = userService.getCurrentUser();
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PostMapping("/admin/create-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponseDto> createUserByAdmin(@RequestBody UserCreateByAdminRequestDto requestDto) {
        UserResponseDto createdUser = userService.createUserByAdmin(requestDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/admin/user-action")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminUserAction(@RequestBody UserAdminActionRequestDto requestDto) {
        boolean success = userService.adminActionOnUser(requestDto);
        if (success) {
            return new ResponseEntity<>("Action completed successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("User not found or invalid action", HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> editProfile(@RequestBody UserProfileEditRequestDto requestDto) {
        UserResponseDto updatedProfile = userService.editProfile(requestDto);
        return new ResponseEntity<>(updatedProfile, HttpStatus.OK);
    }
}