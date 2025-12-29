package com.example.demo.services.impl;

import com.example.demo.dto.user.UserRegisterRequestDto;
import com.example.demo.dto.user.UserResponseDto;
import com.example.demo.mapper.UserMapper;
import com.example.demo.models.Permission;
import com.example.demo.models.User;
import com.example.demo.repositories.PermissionRepository;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUserSuccessTest() {
        // given
        UserRegisterRequestDto requestDto = UserRegisterRequestDto.builder()
                .email("test@example.com")
                .password("123456")
                .repeatPassword("123456")
                .fullName("Test User")
                .build();

        Permission ownerRole = new Permission();
        ownerRole.setPermission("ROLE_OWNER");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setRoles(List.of(ownerRole));

        UserResponseDto responseDto = UserResponseDto.builder().id(1L).build();

        when(userRepository.findByEmail("test@example.com")).thenReturn(null);
        when(permissionRepository.findByPermission("ROLE_OWNER")).thenReturn(ownerRole);
        when(passwordEncoder.encode("123456")).thenReturn("encoded");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when
        boolean result = userService.registerUser(requestDto);

        // then
        assertTrue(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUserEmailExistsTest() {
        // given
        UserRegisterRequestDto requestDto = UserRegisterRequestDto.builder()
                .email("exists@example.com")
                .password("123")
                .repeatPassword("123")
                .build();

        when(userRepository.findByEmail("exists@example.com")).thenReturn(new User());

        // when
        boolean result = userService.registerUser(requestDto);

        // then
        assertFalse(result);
        verify(userRepository, never()).save(any());
    }
}