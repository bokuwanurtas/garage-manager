package com.example.demo.services;

import com.example.demo.dto.user.*;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserRegisterRequestDto requestDto);

    boolean changePassword(UserChangePasswordRequestDto requestDto);

    UserResponseDto getCurrentUser();  // для получения профиля текущего пользователя

    UserResponseDto createUserByAdmin(UserCreateByAdminRequestDto requestDto);

    boolean adminActionOnUser(UserAdminActionRequestDto requestDto);

    UserResponseDto editProfile(UserProfileEditRequestDto requestDto);
}