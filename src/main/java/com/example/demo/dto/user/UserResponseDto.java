package com.example.demo.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserResponseDto {

    private Long id;
    private String email;
    private String fullName;
    private List<String> roles;
}