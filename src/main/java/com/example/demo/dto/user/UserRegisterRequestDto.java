package com.example.demo.dto.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {

    private String email;
    private String password;
    private String repeatPassword;
    private String fullName;
}