package com.example.demo.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateByAdminRequestDto {

    private String email;
    private String password;
    private String fullName;
    private String role;
}