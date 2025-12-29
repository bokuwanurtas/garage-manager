package com.example.demo.services.impl;

import com.example.demo.dto.user.*;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Permission;
import com.example.demo.models.User;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public boolean registerUser(UserRegisterRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()) != null) {
            return false; // email уже занят
        }

        if (!requestDto.getPassword().equals(requestDto.getRepeatPassword())) {
            return false; // пароли не совпадают
        }

        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setFullName(requestDto.getFullName());

        // По умолчанию — роль OWNER
        Permission ownerRole = permissionRepository.findByPermission("ROLE_OWNER");
        if (ownerRole == null) {
            ownerRole = new Permission();
            ownerRole.setPermission("ROLE_OWNER");
            permissionRepository.save(ownerRole);
        }

        user.setRoles(List.of(ownerRole));

        userRepository.save(user);
        return true;
    }

    @Override
    public boolean changePassword(UserChangePasswordRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (!passwordEncoder.matches(requestDto.getOldPassword(), currentUser.getPassword())) {
            return false; // старый пароль неверный
        }

        if (!requestDto.getNewPassword().equals(requestDto.getRepeatNewPassword())) {
            return false; // новые пароли не совпадают
        }

        currentUser.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(currentUser);
        return true;
    }

    @Override
    public UserResponseDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return userMapper.toResponseDto(currentUser);
    }

    @Override
    public UserResponseDto createUserByAdmin(UserCreateByAdminRequestDto requestDto) {
        // Проверка: существует ли уже пользователь с таким email
        if (userRepository.findByEmail(requestDto.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setFullName(requestDto.getFullName());

        // Назначаем роль, которую указал админ
        Permission permission = permissionRepository.findByPermission(requestDto.getRole());
        if (permission == null) {
            throw new RuntimeException("Role not found: " + requestDto.getRole());
        }

        user.setRoles(List.of(permission));

        User saved = userRepository.save(user);
        return userMapper.toResponseDto(saved);
    }

    @Override
    public boolean adminActionOnUser(UserAdminActionRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId()).orElse(null);
        if (user == null) {
            return false;
        }

        // Нельзя блокировать/удалять самого себя (защита)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        if (user.getId().equals(currentUser.getId())) {
            throw new RuntimeException("Cannot block or delete yourself");
        }

        switch (requestDto.getAction()) {
            case "block":
                user.setEnabled(false);
                userRepository.save(user);
                return true;
            case "unblock":
                user.setEnabled(true);
                userRepository.save(user);
                return true;
            case "delete":
                userRepository.delete(user);
                return true;
            default:
                return false;
        }
    }

    @Override
    public UserResponseDto editProfile(UserProfileEditRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        // Проверка: новый email не занят другим пользователем
        User existingUser = userRepository.findByEmail(requestDto.getEmail());
        if (existingUser != null && !existingUser.getId().equals(currentUser.getId())) {
            throw new RuntimeException("Email already in use by another user");
        }

        currentUser.setFullName(requestDto.getFullName());
        currentUser.setEmail(requestDto.getEmail());

        User updated = userRepository.save(currentUser);
        return userMapper.toResponseDto(updated);
    }
}